package com.meli.ipgeolocalization.usecases.model;

public class Currency {

  private String code;
  private String name;

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public static final class CurrencyBuilder {
    private String code;
    private String name;

    public static CurrencyBuilder aCurrencyBuilder() {
      return new CurrencyBuilder();
    }

    public CurrencyBuilder withCode(String code) {
      this.code = code;
      return this;
    }

    public CurrencyBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public Currency build() {
      Currency currency = new Currency();
      currency.code = this.code;
      currency.name = this.name;
      return currency;
    }
  }
}
