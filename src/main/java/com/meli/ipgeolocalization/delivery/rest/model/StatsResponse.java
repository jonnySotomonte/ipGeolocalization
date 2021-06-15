package com.meli.ipgeolocalization.delivery.rest.model;

public class StatsResponse {

  private final Double maxDistance;
  private final Double minDistance;
  private final Double distanceAverage;

  public StatsResponse(Double maxDistance, Double minDistance, Double distanceAverage) {
    this.maxDistance = maxDistance;
    this.minDistance = minDistance;
    this.distanceAverage = distanceAverage;
  }

  public Double getMaxDistance() {
    return maxDistance;
  }

  public Double getMinDistance() {
    return minDistance;
  }

  public Double getDistanceAverage() {
    return distanceAverage;
  }
}
