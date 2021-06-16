package com.meli.ipgeolocalization.repositories.model;

public class ApiError {

  private Integer code;
  private String type;
  private String info;

  // for JSON serialization purposes
  public ApiError() {
  }

  public ApiError(Integer code, String type, String info) {
    this.code = code;
    this.type = type;
    this.info = info;
  }

  public Integer getCode() {
    return code;
  }

  public String getType() {
    return type;
  }

  public String getInfo() {
    return info;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setInfo(String info) {
    this.info = info;
  }
}
