package com.meli.ipgeolocalization.utils;

import com.meli.ipgeolocalization.usecases.model.CountryDistance;
import java.util.Collection;
import java.util.OptionalDouble;

public class StatsUtils {

  public static Double getMax(Collection<CountryDistance> history) {
    if(history.size()==0) {
      return 0.0;
    } else {
      OptionalDouble maxDistance = history.stream()
          .mapToDouble(CountryDistance::getDistance)
          .max();
      return maxDistance.getAsDouble();
    }
  }

  public static Double getMin(Collection<CountryDistance> history) {
    if(history.size()==0) {
      return 0.0;
    } else {
      OptionalDouble minDistance = history.stream()
          .mapToDouble(CountryDistance::getDistance)
          .min();
      return minDistance.getAsDouble();
    }
  }

  public static Double getAverage(Collection<CountryDistance> history) {
    if(history.size()==0) {
      return 0.0;
    } else {
      Double sumDistance = history.stream()
          .mapToDouble(country -> country.getDistance() * country.getInvocations())
          .sum();

      Double sumInvocations = history.stream()
          .mapToDouble(CountryDistance::getInvocations)
          .sum();

      return sumDistance / sumInvocations;
    }

  }

}
