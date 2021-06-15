package com.meli.ipgeolocalization.usecases.model;

import java.util.List;

public class IpTrace {

  private String countryCode;

  private String countryName;

  private Double latitude;

  private Double longitude;

  private List<Language> languages;

  public String getCountryCode() {
    return countryCode;
  }

  public String getCountryName() {
    return countryName;
  }

  public Double getLatitude() {
    return latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public List<Language> getLanguages() {
    return languages;
  }

  public static final class IpTraceBuilder {
    private String countryCode;
    private String countryName;
    private Double latitude;
    private Double longitude;
    private List<Language> languages;

    public static IpTraceBuilder aIpTraceBuilder() {
      return new IpTraceBuilder();
    }

    public IpTraceBuilder withCountryCode(String countryCode) {
      this.countryCode = countryCode;
      return this;
    }

    public IpTraceBuilder withCountryName(String countryName) {
      this.countryName = countryName;
      return this;
    }

    public IpTraceBuilder withLatitude(Double latitude) {
      this.latitude = latitude;
      return this;
    }

    public IpTraceBuilder withLongitude(Double longitude) {
      this.longitude = longitude;
      return this;
    }

    public IpTraceBuilder withLanguages(List<Language> languages) {
      this.languages = languages;
      return this;
    }
    
    public IpTrace build() {
      IpTrace ipTrace = new IpTrace();
      ipTrace.countryCode = this.countryCode;
      ipTrace.countryName = this.countryName;
      ipTrace.latitude = this.latitude;
      ipTrace.longitude = this.longitude;
      ipTrace.languages = this.languages;
      return ipTrace;
    }
  }

}
