package com.meli.ipgeolocalization.usecases;

import com.meli.ipgeolocalization.usecases.model.CountryDistance;
import org.junit.Assert;
import org.junit.Test;

public class UseCasesModelTest {

  @Test
  public void createCountryDistance() {
    CountryDistance countryDistance = new CountryDistance();
    countryDistance.setCountry("Colombia");
    countryDistance.setDistance(4000.0);
    countryDistance.setInvocations(1);

    Assert.assertEquals("Colombia", countryDistance.getCountry());
    Assert.assertEquals(4000.0, countryDistance.getDistance(), 0.0);
    Assert.assertEquals(1, countryDistance.getInvocations(), 0);
  }

}
