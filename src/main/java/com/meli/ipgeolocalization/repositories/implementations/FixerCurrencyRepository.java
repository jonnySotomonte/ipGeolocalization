package com.meli.ipgeolocalization.repositories.implementations;

import com.meli.ipgeolocalization.exceptions.BusinessException;
import com.meli.ipgeolocalization.model.FixerApiErrors;
import com.meli.ipgeolocalization.repositories.interfaces.CurrencyRepository;
import com.meli.ipgeolocalization.repositories.model.FixerResponse;
import com.meli.ipgeolocalization.usecases.model.Currency;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Repository
public class FixerCurrencyRepository implements CurrencyRepository {

  private final Logger logger = LoggerFactory.getLogger(FixerCurrencyRepository.class);
  private final RestTemplate restTemplate;

  private final String fixerUrl;

  private final String fixerAccessKey;

  public FixerCurrencyRepository(RestTemplate restTemplate, @Value("${fixer.url}") String fixerUrl,
      @Value("${fixer.access_key}") String fixerAccessKey) {
    this.restTemplate = restTemplate;
    this.fixerUrl = fixerUrl;
    this.fixerAccessKey = fixerAccessKey;
  }

  @Override
  @Cacheable(value = "country", key = "#to.code")
  public String convertCurrency(String from, Currency to) {
    logger.info("Converting currency from {} to {}", from, to.getCode());
    UriComponentsBuilder uriBuilder = buildUri(from, to.getCode());
    RequestEntity<String> fixerRequest = createHttpRequest(uriBuilder);
    ResponseEntity<FixerResponse> response = restTemplate
        .exchange(fixerRequest, FixerResponse.class);
    FixerResponse convert = response.getBody();
    if(convert.getSuccess() != null && !convert.getSuccess()) {
      Integer errorCode = convert.getError().getCode();
      FixerApiErrors error = FixerApiErrors.getError(errorCode);
      throw new BusinessException(error.getErrorMessage());
    } else {
      logger.info("Currency from {} to {} successfully converted", from, to.getCode());
      String rate = convert.getRates().toString().replace("{", "").replace("}", "");
      return  to.getName() + " ( 1 " + from + " is equal to: " + rate.split("=")[1] + " " + to.getCode() + " )";
    }
  }

  private UriComponentsBuilder buildUri(String from, String to) {
    String ACCESS_KEY_PARAM = "access_key";
    String FROM_PARAM = "base";
    String TO_PARAM = "symbols";

    return UriComponentsBuilder.fromHttpUrl(fixerUrl)
        .queryParam(ACCESS_KEY_PARAM, fixerAccessKey)
        .queryParam(FROM_PARAM, from)
        .queryParam(TO_PARAM, to);
  }

  private RequestEntity<String> createHttpRequest(UriComponentsBuilder uriBuilder) {
    URI resolvedUrl = uriBuilder.build().toUri();
    return new RequestEntity<>(HttpMethod.GET, resolvedUrl);
  }
}
