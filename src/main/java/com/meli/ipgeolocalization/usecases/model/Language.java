package com.meli.ipgeolocalization.usecases.model;

public class Language {
  private String code;
  private String name;

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public static final class LanguageBuilder {
    private String code;
    private String name;

    public static LanguageBuilder aLanguageBuilder() {
      return new LanguageBuilder();
    }

    public LanguageBuilder withCode(String code) {
      this.code = code;
      return this;
    }

    public LanguageBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public Language build() {
      Language language = new Language();
      language.code = this.code;
      language.name = this.name;
      return language;
    }
  }
}
