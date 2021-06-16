package com.meli.ipgeolocalization.repositories;

import com.meli.ipgeolocalization.repositories.model.ApiError;
import com.meli.ipgeolocalization.repositories.model.CountryCurrency;
import com.meli.ipgeolocalization.repositories.model.CountryLanguage;
import com.meli.ipgeolocalization.repositories.model.CountryLocation;
import com.meli.ipgeolocalization.repositories.model.FixerResponse;
import com.meli.ipgeolocalization.repositories.model.IpApiTracerResponse;
import com.meli.ipgeolocalization.repositories.model.RestCountriesResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class RepositoriesModelTest {

  @Test
  public void createApiError() {
    ApiError error = new ApiError();
    error.setCode(1);
    error.setInfo("Info");
    error.setType("Type");

    Assert.assertEquals(1, error.getCode(), 0);
    Assert.assertEquals("Info", error.getInfo());
    Assert.assertEquals("Type", error.getType());

  }

  @Test
  public void createCountryCurrency() {
    CountryCurrency countryCurrency = getCountryCurrency();
    assertCountryCurrency(countryCurrency);
  }

  @Test
  public void createCountryLanguage() {
    CountryLanguage countryLanguage = getCountryLanguage();
    assertLanguage(countryLanguage);
  }

  @Test
  public void createCountryLocation() {
    CountryLanguage countryLanguage = getCountryLanguage();
    CountryLocation countryLocation = new CountryLocation();
    countryLocation.setLanguages(Collections.singletonList(countryLanguage));
    assertLanguage(countryLocation.getLanguages().get(0));
  }

  @Test
  public void createFixerResponse() {
    FixerResponse fixerResponse = new FixerResponse();
    fixerResponse.setError(null);
    fixerResponse.setSuccess(true);
    fixerResponse.setRates("{USD:1.2123}");

    Assert.assertNull(fixerResponse.getError());
    Assert.assertTrue(fixerResponse.getSuccess());
    Assert.assertEquals("{USD:1.2123}", fixerResponse.getRates().toString());
  }

  @Test
  public void createIpApiTracerResponse() {
    CountryLanguage language = new CountryLanguage("es", "Español", "Español");
    List<CountryLanguage> languages = Collections.singletonList(language);

    String countryCode = "CO";
    String countryName = "Colombia";
    Double latitude = 75.40;
    Double longitude = 34.90;
    CountryLocation location = new CountryLocation(languages);

    IpApiTracerResponse response = new IpApiTracerResponse();
    response.setSuccess(true);
    response.setError(null);
    response.setCountryCode(countryCode);
    response.setCountryName(countryName);
    response.setLatitude(latitude);
    response.setLongitude(longitude);
    response.setLocation(location);

    Assert.assertTrue(response.getSuccess());
    Assert.assertNull(response.getError());
    Assert.assertEquals(countryCode, response.getCountryCode());
    Assert.assertEquals(countryName, response.getCountryName());
    Assert.assertEquals(latitude, response.getLatitude());
    Assert.assertEquals(longitude, response.getLongitude());
  }

  @Test
  public void createRestCountriesResponse() {
    CountryCurrency countryCurrency = getCountryCurrency();
    RestCountriesResponse response = new RestCountriesResponse();
    response.setTimezones(Arrays.asList("1", "2"));
    response.setCurrencies(Collections.singletonList(countryCurrency));

    Assert.assertEquals(2, response.getTimezones().size());
    assertCountryCurrency(response.getCurrencies().get(0));
  }

  private CountryCurrency getCountryCurrency() {
    CountryCurrency countryCurrency = new CountryCurrency();
    countryCurrency.setCode("COP");
    countryCurrency.setName("Colombian Peso");
    countryCurrency.setSymbol("$");
    return countryCurrency;
  }

  private CountryLanguage getCountryLanguage() {
    CountryLanguage countryLanguage = new CountryLanguage();
    countryLanguage.setCode("es");
    countryLanguage.setName("Spanish");
    countryLanguage.setNativeLanguage("Español");
    return countryLanguage;
  }

  private void assertLanguage(CountryLanguage countryLanguage) {
    Assert.assertEquals("es", countryLanguage.getCode());
    Assert.assertEquals("Spanish", countryLanguage.getName());
    Assert.assertEquals("Español", countryLanguage.getNativeLanguage());
  }

  private void assertCountryCurrency(CountryCurrency countryCurrency) {
    Assert.assertEquals("COP", countryCurrency.getCode());
    Assert.assertEquals("Colombian Peso", countryCurrency.getName());
    Assert.assertEquals("$", countryCurrency.getSymbol());
  }

}
