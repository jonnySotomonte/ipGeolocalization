package com.meli.ipgeolocalization.repositories.model;

public class CountryCurrency {

  private String code;
  private String name;
  private String symbol;

  // for JSON serialization purposes
  public CountryCurrency() {
  }

  public CountryCurrency(String code, String name, String symbol) {
    this.code = code;
    this.name = name;
    this.symbol = symbol;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }
}
