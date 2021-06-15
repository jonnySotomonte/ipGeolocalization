package com.meli.ipgeolocalization.repositories.model;

import java.util.List;

public class RestCountriesResponse {
  private List<String> timezones;
  private List<CountryCurrency> currencies;

  public List<String> getTimezones() {
    return timezones;
  }

  public List<CountryCurrency> getCurrencies() {
    return currencies;
  }
}
