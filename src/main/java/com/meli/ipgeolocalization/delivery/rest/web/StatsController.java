package com.meli.ipgeolocalization.delivery.rest.web;

import com.meli.ipgeolocalization.delivery.rest.model.StatsResponse;
import com.meli.ipgeolocalization.usecases.interfaces.QueryHistoryStorage;
import com.meli.ipgeolocalization.utils.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController {

  private final Logger logger = LoggerFactory.getLogger(StatsController.class);
  private final QueryHistoryStorage historyStorage;

  public StatsController(
      @Qualifier(value = "localHistory") QueryHistoryStorage historyStorage) {
    this.historyStorage = historyStorage;
  }

  @GetMapping("/stats")
  public ResponseEntity<StatsResponse> getStats() {
    logger.info("getStats request");
    StatsResponse stats = historyStorage.getConsumptionStats();
    logger.info("getStats response: {}", JacksonUtils.getPlainJsonJson(stats));
    return new ResponseEntity<>(stats, HttpStatus.OK);
  }
}
