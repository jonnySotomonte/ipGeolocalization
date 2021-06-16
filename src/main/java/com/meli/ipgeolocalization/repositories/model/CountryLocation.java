package com.meli.ipgeolocalization.repositories.model;

import java.util.List;

public class CountryLocation {

  private List<CountryLanguage> languages;

  // for JSON serialization purposes
  public CountryLocation() {
  }

  public CountryLocation(List<CountryLanguage> languages) {
    this.languages = languages;
  }

  public List<CountryLanguage> getLanguages() {
    return languages;
  }

  public void setLanguages(List<CountryLanguage> languages) {
    this.languages = languages;
  }
}
