package com.meli.ipgeolocalization.repositories;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import com.meli.ipgeolocalization.exceptions.BusinessException;
import com.meli.ipgeolocalization.repositories.implementations.RestCountriesRepository;
import com.meli.ipgeolocalization.repositories.interfaces.CountryRepository;
import com.meli.ipgeolocalization.repositories.model.CountryCurrency;
import com.meli.ipgeolocalization.repositories.model.RestCountriesResponse;
import com.meli.ipgeolocalization.usecases.mappers.DefaultTraceMapper;
import com.meli.ipgeolocalization.usecases.mappers.TraceMapper;
import com.meli.ipgeolocalization.usecases.model.Country;
import java.util.Arrays;
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
public class RestCountriesRepositoryTest {

  private CountryRepository repository;

  @Mock
  private RestTemplate restTemplate;

  @Before
  public void setUp() {
    TraceMapper mapper = new DefaultTraceMapper();
    repository = new RestCountriesRepository(restTemplate, mapper, "http://0.0.0.0/");
  }

  @Test
  public void getByISOCode_Success() {
    List<String> timezones = Arrays.asList("UTC-06:00","UTC-05:00");
    CountryCurrency currency = new CountryCurrency("COP","Colombian Peso","$");
    List<CountryCurrency> currencies = Collections.singletonList(currency);
    RestCountriesResponse restCountriesResponse = new RestCountriesResponse(timezones, currencies);
    Mockito.when(restTemplate.exchange(any(RequestEntity.class), eq(RestCountriesResponse.class)))
        .thenReturn(new ResponseEntity<>(restCountriesResponse, HttpStatus.OK));

    Country country = repository.getByISOCode("CO");
    Assert.assertEquals("COP", country.getCurrency().getCode());
    Assert.assertEquals("Colombian Peso", country.getCurrency().getName());
    Assert.assertEquals(2, country.getTimes().size());
  }

  @Test(expected = BusinessException.class)
  public void getByISOCode_Fail() {

    Mockito.when(restTemplate.exchange(any(RequestEntity.class), eq(RestCountriesResponse.class)))
        .thenThrow(RuntimeException.class);

    repository.getByISOCode("CO");
  }

}
