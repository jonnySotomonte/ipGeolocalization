package com.meli.ipgeolocalization.delivery.rest.web;

import com.meli.ipgeolocalization.IpGeoLocalizationApplicationTests;
import com.meli.ipgeolocalization.delivery.rest.model.IpTraceRequest;
import com.meli.ipgeolocalization.model.ErrorCode;
import com.meli.ipgeolocalization.model.ExceptionInterceptorResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {IpGeoLocalizationApplicationTests.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ValidationsGeoLocalizationController {

  @Autowired
  private TestRestTemplate restTemplate;

  @LocalServerPort
  int randomServerPort;

  @Test
  public void getTrace_InvalidIp() {
    IpTraceRequest request = new IpTraceRequest();
    request.setIp("");
    String traceUrl = "/trace";
    ResponseEntity<ExceptionInterceptorResponse> response = sendRequest(request, traceUrl
    );
    validateFailedResponse(response, "[La ip no puede ser vacia]");
  }

  private <T> ResponseEntity<ExceptionInterceptorResponse> sendRequest(T data, String url) {
    HttpEntity<T> request = new HttpEntity<>(data);
    return restTemplate
        .exchange(url, HttpMethod.POST, request, ExceptionInterceptorResponse.class);
  }

  private void validateFailedResponse(ResponseEntity<ExceptionInterceptorResponse> response, String message) {
    int status = 400;
    ErrorCode errorCode = ErrorCode.INVALID_REQUEST;
    ExceptionInterceptorResponse error = response.getBody();
    Assert.assertEquals(status, response.getStatusCodeValue());
    Assert.assertNotNull(error);
    Assert.assertEquals(errorCode, error.getError().getCode());
    Assert.assertTrue(error.getError().getMsg().contains(message));
  }

}
