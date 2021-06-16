package com.meli.ipgeolocalization.delivery.rest.web;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.meli.ipgeolocalization.delivery.rest.model.IpTraceRequest;
import com.meli.ipgeolocalization.delivery.rest.model.IpTraceResponse;
import com.meli.ipgeolocalization.delivery.rest.model.IpTraceResponse.IpTraceResponseBuilder;
import com.meli.ipgeolocalization.usecases.interfaces.IpTracer;
import com.meli.ipgeolocalization.usecases.interfaces.QueryHistoryStorage;
import java.util.Collections;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class GeoLocalizationControllerTest {

  private GeoLocalizationController controller;

  @Mock
  private IpTracer ipTracer;

  @Mock
  private QueryHistoryStorage historyStorage;

  @Before
  public void setup() {
    controller = new GeoLocalizationController(ipTracer, historyStorage);
  }

  @Test
  public void health() {
    ResponseEntity<String> response = controller.health();
    Assert.assertEquals(200, response.getStatusCodeValue());
    Assert.assertEquals("Todo OK !!!!", response.getBody());
  }

  @Test
  public void getTrace() {
    IpTraceRequest request = new IpTraceRequest();
    request.setIp("1.2.3.4");

    IpTraceResponse traceResponse = IpTraceResponseBuilder.aIpTraceResponseBuilder()
        .withIp("1.2.3.4")
        .withCurrency("Colombian Peso (1 EUR = 4500 COP)")
        .withCurrentDate("2021/06/16")
        .withCountry("Colombia")
        .withISOCode("CO")
        .withLanguages(Collections.singletonList("Español"))
        .withTimes(Collections.singletonList("08:00:00 (UTC-5)"))
        .withEstimatedDistance(4000.0)
        .build();
    when(ipTracer.getTrace(anyString())).thenReturn(traceResponse);

    ResponseEntity<IpTraceResponse> response = controller.getIpTrace(request);

    Assert.assertEquals(200, response.getStatusCodeValue());
    Assert.assertEquals("1.2.3.4", Objects.requireNonNull(response.getBody()).getIp());
    Assert.assertEquals("Colombian Peso (1 EUR = 4500 COP)", Objects.requireNonNull(response.getBody()).getCurrency());
    Assert.assertEquals("2021/06/16", Objects.requireNonNull(response.getBody()).getCurrentDate());
    Assert.assertEquals("Colombia", Objects.requireNonNull(response.getBody()).getCountry());
    Assert.assertEquals("CO", Objects.requireNonNull(response.getBody()).getISOCode());
    Assert.assertEquals("Español", Objects.requireNonNull(response.getBody()).getLanguages().get(0));
    Assert.assertEquals("08:00:00 (UTC-5)", Objects.requireNonNull(response.getBody()).getTimes().get(0));
    Assert.assertEquals(4000.0, Objects.requireNonNull(response.getBody()).getEstimatedDistance(),
        0.0);
  }

}
