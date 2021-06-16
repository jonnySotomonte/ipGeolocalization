package com.meli.ipgeolocalization.interceptors;

import com.meli.ipgeolocalization.model.ErrorCode;
import com.meli.ipgeolocalization.model.ExceptionInterceptorResponse;
import org.junit.Assert;
import org.junit.Test;

public class ExceptionInterceptorTest {

  @Test
  public void handleBusinessException() {
    ExceptionInterceptor interceptor = new ExceptionInterceptor();
    RuntimeException ex = new RuntimeException("There was an error");
    ExceptionInterceptorResponse response = interceptor.handleBusinessException(ex);
    Assert.assertEquals(500, response.getStatus());
    Assert.assertEquals(ErrorCode.GENERAL_EXCEPTION, response.getError().getCode());
    Assert.assertEquals("There was an error", response.getError().getMsg());
  }

}
