package com.meli.ipgeolocalization.repositories.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IpApiTracerResponse {

  // for JSON serialization purposes
  public IpApiTracerResponse() {
  }

  public IpApiTracerResponse(Boolean success,
      ApiError error, String countryCode, String countryName, Double latitude,
      Double longitude, CountryLocation location) {
    this.success = success;
    this.error = error;
    this.countryCode = countryCode;
    this.countryName = countryName;
    this.latitude = latitude;
    this.longitude = longitude;
    this.location = location;
  }

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

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public void setError(ApiError error) {
    this.error = error;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public void setLocation(CountryLocation location) {
    this.location = location;
  }
}
