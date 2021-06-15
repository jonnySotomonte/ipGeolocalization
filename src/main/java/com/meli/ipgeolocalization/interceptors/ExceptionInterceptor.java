package com.meli.ipgeolocalization.interceptors;

import com.meli.ipgeolocalization.exceptions.BusinessException;
import com.meli.ipgeolocalization.model.ErrorCode;
import com.meli.ipgeolocalization.model.ErrorResponse;
import com.meli.ipgeolocalization.model.ExceptionInterceptorResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionInterceptor {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionInterceptorResponse handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      if (error instanceof FieldError) {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
      } else {
        String fieldName = error.getObjectName();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
      }
    });
    ErrorResponse error = new ErrorResponse(errors.values().toString(), ErrorCode.INVALID_REQUEST);
    return new ExceptionInterceptorResponse(HttpStatus.BAD_REQUEST.value(), error);
  }

  @ExceptionHandler(value={BusinessException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionInterceptorResponse handleBusinessException(RuntimeException ex) {
    ErrorResponse error = new ErrorResponse(ex.getMessage(), ErrorCode.GENERAL_EXCEPTION);
    return new ExceptionInterceptorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), error);
  }

}
