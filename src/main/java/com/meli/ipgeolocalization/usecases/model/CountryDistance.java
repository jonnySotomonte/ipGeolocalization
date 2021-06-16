package com.meli.ipgeolocalization.usecases.model;

public class CountryDistance {

  private String country;
  private Double distance;
  private Integer invocations;

  // for JSON serialization purposes
  public CountryDistance() {
  }

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

  public void setCountry(String country) {
    this.country = country;
  }

  public void setDistance(Double distance) {
    this.distance = distance;
  }

  public void setInvocations(Integer invocations) {
    this.invocations = invocations;
  }
}
