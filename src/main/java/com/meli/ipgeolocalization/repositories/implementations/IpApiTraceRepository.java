package com.meli.ipgeolocalization.repositories.implementations;

import com.meli.ipgeolocalization.exceptions.BusinessException;
import com.meli.ipgeolocalization.model.IpAPiTracerErrors;
import com.meli.ipgeolocalization.repositories.interfaces.TracerRepository;
import com.meli.ipgeolocalization.repositories.model.IpApiTracerResponse;
import com.meli.ipgeolocalization.usecases.mappers.TraceMapper;
import com.meli.ipgeolocalization.usecases.model.IpTrace;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Repository
public class IpApiTraceRepository implements TracerRepository {

  private final Logger logger = LoggerFactory.getLogger(IpApiTraceRepository.class);

  private final RestTemplate restTemplate;
  private final TraceMapper mapper;


  private final String ipApiUrl;

  private final String ipAccessKey;

  public IpApiTraceRepository(RestTemplate restTemplate, TraceMapper mapper,
      @Value("${ipapi.url}") String ipApiUrl, @Value("${ipapi.access_key}") String ipAccessKey) {
    this.restTemplate = restTemplate;
    this.mapper = mapper;
    this.ipApiUrl = ipApiUrl;
    this.ipAccessKey = ipAccessKey;
  }

  /**
   * It's a method that call an external API to get information related with the queried ip
   *
   * @param ip Defines the queried ip
   */
  @Override
  public IpTrace getTrace(String ip) {
    try {
      UriComponentsBuilder uriBuilder = buildUri(ip);
      RequestEntity<String> ipTracerRequest = createHttpRequest(uriBuilder);
      ResponseEntity<IpApiTracerResponse> response = restTemplate
          .exchange(ipTracerRequest, IpApiTracerResponse.class);
      IpApiTracerResponse trace = response.getBody();
      if (trace.getSuccess() != null && !trace.getSuccess()) {
        Integer errorCode = trace.getError().getCode();
        IpAPiTracerErrors error = IpAPiTracerErrors.getError(errorCode);
        throw new BusinessException(error.getErrorMessage());
      } else {
        return mapper.mapIpTrace(trace);
      }
    } catch(Exception e) {
      logger.error("ipApiTraceRepository: There was an error, caused by: {}", e.getMessage());
      throw new BusinessException("Hubo un error al consultar la IP ingresada, por favor asegurese de ingresar los datos correctos");
    }

  }

  private UriComponentsBuilder buildUri(String ip) {
    String ACCESS_KEY_PARAM = "access_key";
    return UriComponentsBuilder
        .fromHttpUrl(ipApiUrl.concat(ip))
        .queryParam(ACCESS_KEY_PARAM, ipAccessKey);
  }

  private RequestEntity<String> createHttpRequest(UriComponentsBuilder uriBuilder) {
    URI resolvedUrl = uriBuilder.build().toUri();
    return new RequestEntity<>(HttpMethod.GET, resolvedUrl);
  }
}
