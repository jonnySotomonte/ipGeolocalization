package com.meli.ipgeolocalization.usecases.model;

public class CountryDistance {

  private String country;
  private Double distance;
  private Integer invocations;

  public CountryDistance(String country, Double distance) {
    this.country = country;
    this.distance = distance;
    this.invocations = 1;
  }

  public void addInvocation() {
    this.invocations+=1;
  }

  public String getCountry() {
    return country;
  }

  public Double getDistance() {
    return distance;
  }

  public Integer getInvocations() {
    return invocations;
  }
}
