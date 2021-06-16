package com.meli.ipgeolocalization.delivery.rest.web;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.meli.ipgeolocalization.delivery.rest.model.StatsResponse;
import com.meli.ipgeolocalization.usecases.interfaces.QueryHistoryStorage;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class StatsControllerTest {
  private StatsController controller;

  @Mock
  private QueryHistoryStorage historyStorage;

  @Before
  public void setup() {
    controller = new StatsController(historyStorage);
  }

  @Test
  public void getStats() {
    StatsResponse statsResponse = new StatsResponse(10000.0, 5000.0, 7500.0);
    when(historyStorage.getConsumptionStats()).thenReturn(statsResponse);

    ResponseEntity<StatsResponse> response = controller.getStats();

    Assert.assertEquals(200, response.getStatusCodeValue());
    Assert.assertEquals(10000.0, Objects.requireNonNull(response.getBody()).getMaxDistance(), 0.0);
    Assert.assertEquals(5000.0, Objects.requireNonNull(response.getBody()).getMinDistance(), 0.0);
    Assert.assertEquals(7500.0, Objects.requireNonNull(response.getBody()).getDistanceAverage(), 0.0);
  }
}
