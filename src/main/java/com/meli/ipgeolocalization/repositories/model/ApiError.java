package com.meli.ipgeolocalization.repositories.model;

public class ApiError {

  private Integer code;
  private String type;
  private String info;

  public Integer getCode() {
    return code;
  }

  public String getType() {
    return type;
  }

  public String getInfo() {
    return info;
  }
}
