package com.meli.ipgeolocalization.usecases;

import com.meli.ipgeolocalization.delivery.rest.model.StatsResponse;
import com.meli.ipgeolocalization.usecases.implementations.LocalQueryHistoryStorage;
import com.meli.ipgeolocalization.usecases.interfaces.QueryHistoryStorage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class LocalQueryHistoryStorageTest {

  private QueryHistoryStorage queryHistoryStorage;

  @Before
  public void setup() {
    queryHistoryStorage = new LocalQueryHistoryStorage();
  }

  @Test
  public void getConsumptionStats_ZeroRecords() {

    StatsResponse stats = queryHistoryStorage.getConsumptionStats();

    Assert.assertEquals(0.0, stats.getMinDistance(), 0.0);
    Assert.assertEquals(0.0, stats.getMaxDistance(), 0.0);
    Assert.assertEquals(0.0, stats.getDistanceAverage(), 0.0);

  }

  @Test
  public void getConsumptionStats() {
    String colombia = "Colombia";
    String unitedStates = "United States";
    String france = "France";

    Double colombiaDistance = 4000.0;
    Double unitedStatesDistance = 8000.0;
    Double franceDistance = 7000.0;

    queryHistoryStorage.registerCountryDistance(colombia, colombiaDistance);
    queryHistoryStorage.registerCountryDistance(colombia, colombiaDistance);
    queryHistoryStorage.registerCountryDistance(colombia, colombiaDistance);

    queryHistoryStorage.registerCountryDistance(unitedStates, unitedStatesDistance);
    queryHistoryStorage.registerCountryDistance(unitedStates, unitedStatesDistance);

    queryHistoryStorage.registerCountryDistance(france, franceDistance);

    StatsResponse stats = queryHistoryStorage.getConsumptionStats();

    Assert.assertEquals(4000.0, stats.getMinDistance(), 0.0);
    Assert.assertEquals(8000.0, stats.getMaxDistance(), 0.0);
    Assert.assertEquals(5833.33, stats.getDistanceAverage(), 0.01);

  }

}
