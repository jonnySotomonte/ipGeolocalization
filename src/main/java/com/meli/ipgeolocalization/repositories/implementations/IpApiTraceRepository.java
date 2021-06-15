package com.meli.ipgeolocalization.repositories.implementations;

import com.meli.ipgeolocalization.exceptions.BusinessException;
import com.meli.ipgeolocalization.model.IpAPiTracerErrors;
import com.meli.ipgeolocalization.repositories.interfaces.TracerRepository;
import com.meli.ipgeolocalization.repositories.model.IpApiTracerResponse;
import com.meli.ipgeolocalization.usecases.model.IpTrace;
import com.meli.ipgeolocalization.utils.JacksonUtils;
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
import com.meli.ipgeolocalization.usecases.mappers.TraceMapper;

@Repository
public class IpApiTraceRepository implements TracerRepository {

  private final RestTemplate restTemplate;
  private final TraceMapper mapper;

  @Value("${ipapi.url}")
  private String ipApiUrl;

  @Value("${ipapi.access_key}")
  private String ipAccessKey;

  public IpApiTraceRepository(RestTemplate restTemplate, TraceMapper mapper) {
    this.restTemplate = restTemplate;
    this.mapper = mapper;
  }

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
