package com.meli.ipgeolocalization.repositories.model;

public class FixerResponse {
  private Boolean success;

  private ApiError error;

  private Object rates;

  public Boolean getSuccess() {
    return success;
  }

  public ApiError getError() {
    return error;
  }

  public Object getRates() {
    return rates;
  }
}
