package com.meli.ipgeolocalization.repositories.model;

public class FixerResponse {
  private Boolean success;

  private ApiError error;

  private Object rates;

  // for JSON serialization purposes
  public FixerResponse() {
  }

  public FixerResponse(Boolean success, ApiError error, Object rates) {
    this.success = success;
    this.error = error;
    this.rates = rates;
  }

  public Boolean getSuccess() {
    return success;
  }

  public ApiError getError() {
    return error;
  }

  public Object getRates() {
    return rates;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public void setError(ApiError error) {
    this.error = error;
  }

  public void setRates(Object rates) {
    this.rates = rates;
  }
}
