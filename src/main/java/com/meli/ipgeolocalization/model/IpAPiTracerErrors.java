package com.meli.ipgeolocalization.model;

public enum IpAPiTracerErrors {

  INVALID_ACCESS_KEY("Parámetro access key de IPAPI inválido", 101),
  INVALID_USER("Usuario bloqueado para realizar la consulta de IPAPI", 102),
  INVALID_METHOD("La función de IPAPI a consultar es inválida", 103),
  TOO_MANY_REQUEST("Ha superada la cantidad de llamados a IPAPI permitidos", 104),
  UNAUTHORIZED("Accesso restringido a IPAPI", 105),
  INVALID_FIELDS("Campos inválidos enviados en la consulta a IPAPI", 301),
  TOO_MANY_IPS("Superó la cantidad de ips a consultar permitidas en IPAPI", 302),
  UNSUPPORTED_BATCH("Proceso en batch no soportado en IPAPI", 303),
  NOT_FOUND("Ip no encontrada", 404),
  DEFAULT("Se produjo un error consultando en IPAPI", 500);

  private String errorMessage;
  private Integer errorCode;

  IpAPiTracerErrors(String errorMessage, Integer errorCode) {
    this.errorMessage = errorMessage;
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public static IpAPiTracerErrors getError(int code) {
    for (IpAPiTracerErrors traceError : IpAPiTracerErrors.values()) {
      if (traceError.errorCode == code) return traceError;
    }
    return DEFAULT;
  }
}
