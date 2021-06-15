package com.meli.ipgeolocalization.repositories.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryLanguage {

  private String code;
  private String name;

  @JsonProperty("native")
  private String nativeLanguage;

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public String getNativeLanguage() {
    return nativeLanguage;
  }
}
