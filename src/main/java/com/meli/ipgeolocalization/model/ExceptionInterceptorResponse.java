package com.meli.ipgeolocalization.model;

public class ExceptionInterceptorResponse {
  private int status;
  private ErrorResponse error;

  public ExceptionInterceptorResponse(int status, ErrorResponse error) {
    this.status = status;
    this.error = error;
  }

  public int getStatus() {
    return status;
  }

  public ErrorResponse getError() {
    return error;
  }
}
