package com.meli.ipgeolocalization.model;

public class ErrorResponse {
  private String msg;
  private ErrorCode code;

  public ErrorResponse(String msg, ErrorCode code) {
    this.msg = msg;
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public ErrorCode getCode() {
    return code;
  }
}
