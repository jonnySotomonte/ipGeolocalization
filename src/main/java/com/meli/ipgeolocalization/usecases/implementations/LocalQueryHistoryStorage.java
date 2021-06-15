package com.meli.ipgeolocalization.usecases.implementations;

import com.meli.ipgeolocalization.delivery.rest.model.StatsResponse;
import com.meli.ipgeolocalization.usecases.interfaces.QueryHistoryStorage;
import com.meli.ipgeolocalization.usecases.model.CountryDistance;
import com.meli.ipgeolocalization.utils.JacksonUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LocalQueryHistoryStorage implements QueryHistoryStorage {

  private final Logger logger = LoggerFactory.getLogger(LocalQueryHistoryStorage.class);
  private Map<String, CountryDistance> localHistory = new HashMap<>();

  @Override
  public void registerCountryDistance(String country, Double distance) {
    CountryDistance registeredCountryDistance = localHistory.get(country);
    if(registeredCountryDistance == null) {
      CountryDistance countryDistance = new CountryDistance(country, distance);
      localHistory.put(country, countryDistance);
    } else {
      registeredCountryDistance.addInvocation();
      localHistory.put(country, registeredCountryDistance);
    }
    logger.info("Local history: {}", JacksonUtils.getPlainJsonJson(localHistory));
  }

  @Override
  public StatsResponse getConsumptionStats() {
    Double max = getMax();
    Double min = getMin();
    Double average = getAverage();
    return new StatsResponse(max, min, average);
  }

  private Double getMax() {
    if(localHistory.values().size()==0) {
      return 0.0;
    } else {
      OptionalDouble maxDistance = localHistory.values().stream()
          .mapToDouble(CountryDistance::getDistance)
          .max();
      return maxDistance.getAsDouble();
    }
  }

  private Double getMin() {
    if(localHistory.values().size()==0) {
      return 0.0;
    } else {
      OptionalDouble minDistance = localHistory.values().stream()
          .mapToDouble(CountryDistance::getDistance)
          .min();
      return minDistance.getAsDouble();
    }
  }

  private Double getAverage() {
    if(localHistory.values().size()==0) {
      return 0.0;
    } else {
      Double sumDistance = localHistory.values().stream()
          .mapToDouble(country -> country.getDistance() * country.getInvocations())
          .sum();

      Double sumInvocations = localHistory.values().stream()
          .mapToDouble(CountryDistance::getInvocations)
          .sum();

      return sumDistance / sumInvocations;
    }

  }
}
