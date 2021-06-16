package com.meli.ipgeolocalization.repositories.model;

import java.util.List;

public class RestCountriesResponse {
  private List<String> timezones;
  private List<CountryCurrency> currencies;

  // for JSON serialization purposes
  public RestCountriesResponse() {
  }

  public RestCountriesResponse(List<String> timezones, List<CountryCurrency> currencies) {
    this.timezones = timezones;
    this.currencies = currencies;
  }

  public List<String> getTimezones() {
    return timezones;
  }

  public List<CountryCurrency> getCurrencies() {
    return currencies;
  }

  public void setTimezones(List<String> timezones) {
    this.timezones = timezones;
  }

  public void setCurrencies(
      List<CountryCurrency> currencies) {
    this.currencies = currencies;
  }
}
