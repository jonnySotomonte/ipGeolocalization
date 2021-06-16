package com.meli.ipgeolocalization.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.meli.ipgeolocalization.delivery.rest.mappers.DefaultIpTracerMapper;
import com.meli.ipgeolocalization.delivery.rest.mappers.IpTraceMapper;
import com.meli.ipgeolocalization.delivery.rest.model.IpTraceResponse;
import com.meli.ipgeolocalization.repositories.interfaces.CountryRepository;
import com.meli.ipgeolocalization.repositories.interfaces.CurrencyRepository;
import com.meli.ipgeolocalization.repositories.interfaces.TracerRepository;
import com.meli.ipgeolocalization.usecases.implementations.DefaultIpTracer;
import com.meli.ipgeolocalization.usecases.implementations.HaversineDistanceCalculator;
import com.meli.ipgeolocalization.usecases.interfaces.DistanceCalculator;
import com.meli.ipgeolocalization.usecases.interfaces.IpTracer;
import com.meli.ipgeolocalization.usecases.model.Country;
import com.meli.ipgeolocalization.usecases.model.Country.CountryBuilder;
import com.meli.ipgeolocalization.usecases.model.Currency;
import com.meli.ipgeolocalization.usecases.model.Currency.CurrencyBuilder;
import com.meli.ipgeolocalization.usecases.model.IpTrace;
import com.meli.ipgeolocalization.usecases.model.IpTrace.IpTraceBuilder;
import com.meli.ipgeolocalization.usecases.model.Language;
import com.meli.ipgeolocalization.usecases.model.Language.LanguageBuilder;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultIpTracerTest {

  private IpTracer ipTracer;

  @Mock
  private TracerRepository tracerRepository;

  @Mock
  private CountryRepository countryRepository;

  @Mock
  private CurrencyRepository currencyRepository;

  @Before
  public void setup() {
    IpTraceMapper mapper = new DefaultIpTracerMapper();
    DistanceCalculator distanceCalculator = new HaversineDistanceCalculator();
    ipTracer = new DefaultIpTracer(tracerRepository, countryRepository, currencyRepository, distanceCalculator, mapper);
  }

  @Test
  public void getTrace() {
    when(tracerRepository.getTrace(anyString())).thenReturn(buildTraceResponse());
    when(countryRepository.getByISOCode(anyString())).thenReturn(buildCountryResponse());
    when(currencyRepository.convertCurrency(any(), any())).thenReturn(buildCurrencyResponse());
    IpTraceResponse response = ipTracer.getTrace("1.2.3.4");
    Assert.assertNotNull(response);
    Assert.assertEquals("1.2.3.4", response.getIp());
    Assert.assertNotNull(response.getCurrentDate());
    Assert.assertEquals("Argentina", response.getCountry());
    Assert.assertEquals("AR", response.getISOCode());
    Assert.assertEquals("Español ( ES )", response.getLanguages().get(0));
    Assert.assertEquals("Peso Argentino (1 EUR = 200 ARP)", response.getCurrency());
    Assert.assertEquals("07:00:00 (UTC-06:00)", response.getTimes().get(0));
    Assert.assertEquals(0.0, response.getEstimatedDistance(), 0.0);
  }

  private IpTrace buildTraceResponse() {

    Language language = LanguageBuilder.aLanguageBuilder().withCode("ES").withName("Español").build();
    List<Language> languages = Collections.singletonList(language);

    return IpTraceBuilder.aIpTraceBuilder()
        .withCountryCode("AR")
        .withCountryName("Argentina")
        .withLatitude(-34.611778259277344)
        .withLongitude(-58.41730880737305)
        .withLanguages(languages)
        .build();
  }

  private Country buildCountryResponse() {

    List<String> times = Collections.singletonList("07:00:00 (UTC-06:00)");
    Currency currency = CurrencyBuilder.aCurrencyBuilder()
        .withCode("ARP")
        .withName("Peso Argentino")
        .build();

    return CountryBuilder.aCountryBuilder()
        .withTimes(times)
        .withCurrency(currency)
        .build();
  }

  private String buildCurrencyResponse() {
    return "Peso Argentino (1 EUR = 200 ARP)";
  }

}
