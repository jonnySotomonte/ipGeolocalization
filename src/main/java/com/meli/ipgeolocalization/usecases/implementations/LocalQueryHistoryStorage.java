package com.meli.ipgeolocalization.usecases.implementations;

import com.meli.ipgeolocalization.delivery.rest.model.StatsResponse;
import com.meli.ipgeolocalization.usecases.interfaces.QueryHistoryStorage;
import com.meli.ipgeolocalization.usecases.model.CountryDistance;
import com.meli.ipgeolocalization.utils.JacksonUtils;
import com.meli.ipgeolocalization.utils.StatsUtils;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "localHistory")
public class LocalQueryHistoryStorage implements QueryHistoryStorage {

  private final Logger logger = LoggerFactory.getLogger(LocalQueryHistoryStorage.class);
  private Map<String, CountryDistance> localHistory = new HashMap<>();

  /**
   * It's a method that register a country distance record in a local data structure
   *
   * @param country Defines the country's name
   * @param distance Defines the distance between Argentina and the country
   */
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
    Double max = StatsUtils.getMax(localHistory.values());
    Double min = StatsUtils.getMin(localHistory.values());
    Double average = StatsUtils.getAverage(localHistory.values());
    return new StatsResponse(max, min, average);
  }
}
