package com.meli.ipgeolocalization.repositories.implementations;

import com.meli.ipgeolocalization.exceptions.BusinessException;
import com.meli.ipgeolocalization.repositories.interfaces.CountryRepository;
import com.meli.ipgeolocalization.repositories.model.RestCountriesResponse;
import com.meli.ipgeolocalization.usecases.mappers.TraceMapper;
import com.meli.ipgeolocalization.usecases.model.Country;
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
public class RestCountriesRepository implements CountryRepository {

  private final Logger logger = LoggerFactory.getLogger(RestCountriesRepository.class);
  private final RestTemplate restTemplate;
  private final TraceMapper mapper;

  private final String restCountriesUrl;

  public RestCountriesRepository(RestTemplate restTemplate, TraceMapper mapper,
      @Value("${restCountries.url}") String restCountriesUrl) {
    this.restTemplate = restTemplate;
    this.mapper = mapper;
    this.restCountriesUrl = restCountriesUrl;
  }

  @Override
  @Cacheable(value = "country", key = "#code")
  public Country getByISOCode(String code) {
    try {
      logger.info("Querying country {} by ISOCode", code);
      UriComponentsBuilder uriBuilder = buildUri(code);
      RequestEntity<String> restCountriesRequest = createHttpRequest(uriBuilder);
      ResponseEntity<RestCountriesResponse> response = restTemplate.exchange(restCountriesRequest, RestCountriesResponse.class);
      logger.info("Country {} successfully queried", code);
      RestCountriesResponse country = response.getBody();
      return mapper.mapCountry(country);
    } catch (Exception e) {
      throw new BusinessException("Hubo un error consultando la información del pais asociado a la IP consultada");
    }
  }

  private UriComponentsBuilder buildUri(String code) {
    return UriComponentsBuilder
        .fromHttpUrl(restCountriesUrl.concat(code));
  }

  private RequestEntity<String> createHttpRequest(UriComponentsBuilder uriBuilder) {
    URI resolvedUrl = uriBuilder.build().toUri();
    return new RequestEntity<>(HttpMethod.GET, resolvedUrl);
  }
}
