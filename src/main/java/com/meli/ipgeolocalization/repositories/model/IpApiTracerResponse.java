package com.meli.ipgeolocalization.repositories.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IpApiTracerResponse {

  private Boolean success;

  private ApiError error;

  @JsonProperty("country_code")
  private String countryCode;

  @JsonProperty("country_name")
  private String countryName;

  private Double latitude;

  private Double longitude;

  private CountryLocation location;

  public Boolean getSuccess() {
    return success;
  }

  public ApiError getError() {
    return error;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public String getCountryName() {
    return countryName;
  }

  public Double getLatitude() {
    return latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public CountryLocation getLocation() {
    return location;
  }
}
