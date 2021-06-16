package com.meli.ipgeolocalization.repositories.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryLanguage {

  private String code;
  private String name;

  @JsonProperty("native")
  private String nativeLanguage;

  public CountryLanguage() {
  }

  public CountryLanguage(String code, String name, String nativeLanguage) {
    this.code = code;
    this.name = name;
    this.nativeLanguage = nativeLanguage;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public String getNativeLanguage() {
    return nativeLanguage;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setNativeLanguage(String nativeLanguage) {
    this.nativeLanguage = nativeLanguage;
  }
}
