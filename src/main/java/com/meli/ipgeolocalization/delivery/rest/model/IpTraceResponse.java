package com.meli.ipgeolocalization.delivery.rest.model;

import java.util.List;

public class IpTraceResponse {
  private String ip;
  private String currentDate;
  private String country;
  private String ISOCode;
  private List<String> languages;
  private String currency;
  private List<String> times;
  private Double estimatedDistance;

  public String getIp() {
    return ip;
  }

  public String getCurrentDate() {
    return currentDate;
  }

  public String getCountry() {
    return country;
  }

  public String getISOCode() {
    return ISOCode;
  }

  public List<String> getLanguages() {
    return languages;
  }

  public String getCurrency() {
    return currency;
  }

  public List<String> getTimes() {
    return times;
  }

  public Double getEstimatedDistance() {
    return estimatedDistance;
  }

  public static final class IpTraceResponseBuilder {
    private String ip;
    private String currentDate;
    private String country;
    private String ISOCode;
    private List<String> languages;
    private String currency;
    private List<String> times;
    private Double estimatedDistance;

    public static IpTraceResponseBuilder aIpTraceResponseBuilder() {
      return new IpTraceResponseBuilder();
    }

    public IpTraceResponseBuilder withIp(String ip) {
      this.ip = ip;
      return this;
    }

    public IpTraceResponseBuilder withCurrentDate(String currentDate) {
      this.currentDate = currentDate;
      return this;
    }

    public IpTraceResponseBuilder withCountry(String country) {
      this.country = country;
      return this;
    }

    public IpTraceResponseBuilder withISOCode(String ISOCode) {
      this.ISOCode = ISOCode;
      return this;
    }

    public IpTraceResponseBuilder withLanguages(List<String> languages) {
      this.languages = languages;
      return this;
    }

    public IpTraceResponseBuilder withCurrency(String currency) {
      this.currency = currency;
      return this;
    }

    public IpTraceResponseBuilder withTimes(List<String> times) {
      this.times = times;
      return this;
    }

    public IpTraceResponseBuilder withEstimatedDistance(Double estimatedDistance) {
      this.estimatedDistance = estimatedDistance;
      return this;
    }

    public IpTraceResponse build() {
      IpTraceResponse response = new IpTraceResponse();
      response.ip = this.ip;
      response.currentDate = this.currentDate;
      response.country = this.country;
      response.ISOCode = this.ISOCode;
      response.languages = this.languages;
      response.currency = this.currency;
      response.times = this.times;
      response.estimatedDistance = this.estimatedDistance;
      return response;
    }
  }
}
