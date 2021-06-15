package com.meli.ipgeolocalization.delivery.rest.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class IpTraceRequest {

  @NotNull(message = "La ip no puede ser nula")
  @NotBlank(message = "La ip no puede ser vacia")
  private String ip;

  public String getIp() {
    return ip;
  }
}
