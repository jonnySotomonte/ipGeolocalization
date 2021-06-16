package com.meli.ipgeolocalization.repositories;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import com.meli.ipgeolocalization.exceptions.BusinessException;
import com.meli.ipgeolocalization.repositories.implementations.IpApiTraceRepository;
import com.meli.ipgeolocalization.repositories.interfaces.TracerRepository;
import com.meli.ipgeolocalization.repositories.model.ApiError;
import com.meli.ipgeolocalization.repositories.model.CountryLanguage;
import com.meli.ipgeolocalization.repositories.model.CountryLocation;
import com.meli.ipgeolocalization.repositories.model.IpApiTracerResponse;
import com.meli.ipgeolocalization.usecases.mappers.DefaultTraceMapper;
import com.meli.ipgeolocalization.usecases.mappers.TraceMapper;
import com.meli.ipgeolocalization.usecases.model.IpTrace;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class IpApiTraceRepositoryTest {

  private TracerRepository repository;

  @Mock
  private RestTemplate restTemplate;

  @Before
  public void setUp() {
    TraceMapper mapper = new DefaultTraceMapper();
    repository = new IpApiTraceRepository(restTemplate, mapper, "http://0.0.0.0/", "dummyAccessKey");
  }

  @Test
  public void getTrace_Success() {
    CountryLanguage language = new CountryLanguage("es", "Español", "Español");
    List<CountryLanguage> languages = Collections.singletonList(language);

    Boolean success = null;
    String countryCode = "CO";
    String countryName = "Colombia";
    Double latitude = 75.40;
    Double longitude = 34.90;
    CountryLocation location = new CountryLocation(languages);

    IpApiTracerResponse ipApiTracerResponse = new IpApiTracerResponse(success, null, countryCode, countryName, latitude, longitude, location);

    Mockito.when(restTemplate.exchange(any(RequestEntity.class), eq(IpApiTracerResponse.class)))
        .thenReturn(new ResponseEntity<>(ipApiTracerResponse, HttpStatus.OK));

    IpTrace trace = repository.getTrace("1.2.3.4");

    Assert.assertEquals("Colombia", trace.getCountryName());
    Assert.assertEquals("CO", trace.getCountryCode());
    Assert.assertEquals(75.40, trace.getLatitude(), 0.0);
    Assert.assertEquals(34.90, trace.getLongitude(), 0.0);
    Assert.assertEquals("Español", trace.getLanguages().get(0).getName());
    Assert.assertEquals("es", trace.getLanguages().get(0).getCode());
  }

  @Test(expected = BusinessException.class)
  public void getTrace_Fail() {
    ApiError error = new ApiError(101,"Invalid Access key","The request failed caused by the access key");
    IpApiTracerResponse ipApiTracerResponse = new IpApiTracerResponse(false, error, null, null, null, null, null);
    Mockito.when(restTemplate.exchange(any(RequestEntity.class), eq(IpApiTracerResponse.class)))
        .thenReturn(new ResponseEntity<>(ipApiTracerResponse, HttpStatus.OK));
    repository.getTrace("1.2.3.4");
  }

  @Test(expected = BusinessException.class)
  public void getTrace_Fail_DefaultError() {
    ApiError error = new ApiError(304,"General Error","There was a general error");
    IpApiTracerResponse ipApiTracerResponse = new IpApiTracerResponse(false, error, null, null, null, null, null);
    Mockito.when(restTemplate.exchange(any(RequestEntity.class), eq(IpApiTracerResponse.class)))
        .thenReturn(new ResponseEntity<>(ipApiTracerResponse, HttpStatus.OK));
    repository.getTrace("1.2.3.4");
  }

}
