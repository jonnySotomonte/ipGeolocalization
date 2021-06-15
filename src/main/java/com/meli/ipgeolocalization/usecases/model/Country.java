package com.meli.ipgeolocalization.usecases.model;

import java.util.List;

public class Country {

  private List<String> times;
  private Currency currency;

  public List<String> getTimes() {
    return times;
  }

  public Currency getCurrency() {
    return currency;
  }

  public static final class CountryBuilder {
    private List<String> times;
    private Currency currency;

    public static CountryBuilder aCountryBuilder() {
      return new CountryBuilder();
    }

    public CountryBuilder withTimes(List<String> times) {
      this.times = times;
      return this;
    }

    public CountryBuilder withCurrency(Currency currency) {
      this.currency = currency;
      return this;
    }

    public Country build() {
      Country country = new Country();
      country.times = this.times;
      country.currency = this.currency;
      return country;
    }
  }
}
