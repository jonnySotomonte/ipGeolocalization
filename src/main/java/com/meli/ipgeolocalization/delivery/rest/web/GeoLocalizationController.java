package com.meli.ipgeolocalization.delivery.rest.web;

import com.meli.ipgeolocalization.delivery.rest.model.IpTraceRequest;
import com.meli.ipgeolocalization.delivery.rest.model.IpTraceResponse;
import com.meli.ipgeolocalization.delivery.rest.model.StatsResponse;
import com.meli.ipgeolocalization.usecases.interfaces.IpTracer;
import com.meli.ipgeolocalization.usecases.interfaces.QueryHistoryStorage;
import com.meli.ipgeolocalization.utils.JacksonUtils;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class GeoLocalizationController {

  private final Logger logger = LoggerFactory.getLogger(GeoLocalizationController.class);
  private final IpTracer ipTracer;
  private final QueryHistoryStorage historyStorage;

  public GeoLocalizationController(IpTracer ipTracer,
      QueryHistoryStorage historyStorage) {
    this.ipTracer = ipTracer;
    this.historyStorage = historyStorage;
  }

  @GetMapping("/")
  public ResponseEntity<String> health() {
    return new ResponseEntity<>("Todo OK !!!!" , HttpStatus.OK);
  }

  @PostMapping("/trace")
  public ResponseEntity<IpTraceResponse> getIpTrace(@Valid @RequestBody IpTraceRequest request) {
    logger.info("getIpTrace request: {}", JacksonUtils.getPlainJsonJson(request));
    IpTraceResponse response = ipTracer.getTrace(request.getIp());
    historyStorage.registerCountryDistance(response.getCountry(), response.getEstimatedDistance());
    logger.info("getIpTrace response: {}", JacksonUtils.getPlainJsonJson(response));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/stats")
  public ResponseEntity<StatsResponse> getStats() {
    logger.info("getStats request");
    StatsResponse stats = historyStorage.getConsumptionStats();
    logger.info("getStats response: {}", JacksonUtils.getPlainJsonJson(stats));
    return new ResponseEntity<>(stats, HttpStatus.OK);
  }

}
