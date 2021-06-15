package com.meli.ipgeolocalization.usecases.interfaces;

import com.meli.ipgeolocalization.delivery.rest.model.StatsResponse;

public interface QueryHistoryStorage {

  void registerCountryDistance(String country, Double distance);

  StatsResponse getConsumptionStats();

}
