package com.meli.ipgeolocalization.model;

public enum FixerApiErrors {
  INVALID_ACCESS_KEY("Parámetro access key de Fixer inválido", 101),
  INVALID_USER("Usuario bloqueado para realizar la consulta de Fixer", 102),
  INVALID_METHOD("La función de Fixer a consultar es inválida", 103),
  TOO_MANY_REQUEST("Ha superada la cantidad de llamados a Fixer permitidos", 104),
  UNAUTHORIZED("Accesso restringido a Fixer", 105),
  NO_RESULTS("La consulta a Fixer no retorn'o resultados", 106),
  INVALID_BASE("El codigo de la moneda base a convertir es inválida", 201),
  INVALID_SYMBOL("Uno o más codigos de moneda objetivo a convertir es inválida", 202),
  NOT_FOUND("Moneda a convertir no encontrada", 404),
  DEFAULT("Se produjo un error consultando en IPAPI", 500);

  private String errorMessage;
  private Integer errorCode;

  FixerApiErrors(String errorMessage, Integer errorCode) {
    this.errorMessage = errorMessage;
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public static FixerApiErrors getError(int code) {
    for (FixerApiErrors fixerError : FixerApiErrors.values()) {
      if (fixerError.errorCode == code) return fixerError;
    }
    return DEFAULT;
  }
}
