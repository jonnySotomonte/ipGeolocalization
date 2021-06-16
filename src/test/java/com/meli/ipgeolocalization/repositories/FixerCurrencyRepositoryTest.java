package com.meli.ipgeolocalization.repositories;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import com.meli.ipgeolocalization.exceptions.BusinessException;
import com.meli.ipgeolocalization.repositories.implementations.FixerCurrencyRepository;
import com.meli.ipgeolocalization.repositories.implementations.RestCountriesRepository;
import com.meli.ipgeolocalization.repositories.interfaces.CurrencyRepository;
import com.meli.ipgeolocalization.repositories.model.ApiError;
import com.meli.ipgeolocalization.repositories.model.FixerResponse;
import com.meli.ipgeolocalization.repositories.model.RestCountriesResponse;
import com.meli.ipgeolocalization.usecases.mappers.DefaultTraceMapper;
import com.meli.ipgeolocalization.usecases.mappers.TraceMapper;
import com.meli.ipgeolocalization.usecases.model.Currency;
import com.meli.ipgeolocalization.usecases.model.Currency.CurrencyBuilder;
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
public class FixerCurrencyRepositoryTest {

  private CurrencyRepository repository;

  @Mock
  private RestTemplate restTemplate;

  @Before
  public void setUp() {
    repository = new FixerCurrencyRepository(restTemplate, "http://0.0.0.0/", "dummyAccessKey");
  }

  @Test
  public void convertCurrency_Success() {
    FixerResponse fixerResponse = new FixerResponse(true, null, "COP=4500.50");
    Mockito.when(restTemplate.exchange(any(RequestEntity.class), eq(FixerResponse.class)))
        .thenReturn(new ResponseEntity<>(fixerResponse, HttpStatus.OK));

    String from = "EUR";
    Currency to = CurrencyBuilder.aCurrencyBuilder().withCode("COP").withName("Colombian Peso").build();

    String convertedCurrency = repository.convertCurrency(from, to);
    Assert.assertEquals("Colombian Peso ( 1 EUR is equal to: 4500.50 COP )", convertedCurrency);
  }

  @Test(expected = BusinessException.class)
  public void convertCurrency_Fail() {
    ApiError error = new ApiError(101,"Invalid Access key","The request failed caused by the access key");
    FixerResponse fixerResponse = new FixerResponse(false, error, null);
    Mockito.when(restTemplate.exchange(any(RequestEntity.class), eq(FixerResponse.class)))
        .thenReturn(new ResponseEntity<>(fixerResponse, HttpStatus.OK));

    String from = "EUR";
    Currency to = CurrencyBuilder.aCurrencyBuilder().withCode("COP").withName("Colombian Peso").build();

    repository.convertCurrency(from, to);
  }

  @Test(expected = BusinessException.class)
  public void convertCurrency_Fail_DefaultError() {
    ApiError error = new ApiError(304,"General Error","There was a general error");
    FixerResponse fixerResponse = new FixerResponse(false, error, null);
    Mockito.when(restTemplate.exchange(any(RequestEntity.class), eq(FixerResponse.class)))
        .thenReturn(new ResponseEntity<>(fixerResponse, HttpStatus.OK));

    String from = "EUR";
    Currency to = CurrencyBuilder.aCurrencyBuilder().withCode("COP").withName("Colombian Peso").build();

    repository.convertCurrency(from, to);
  }

}
