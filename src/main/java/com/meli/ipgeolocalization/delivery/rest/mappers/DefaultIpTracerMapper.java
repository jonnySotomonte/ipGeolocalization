package com.meli.ipgeolocalization.delivery.rest.mappers;

import com.meli.ipgeolocalization.delivery.rest.model.IpTraceResponse;
import com.meli.ipgeolocalization.delivery.rest.model.IpTraceResponse.IpTraceResponseBuilder;
import com.meli.ipgeolocalization.usecases.model.Country;
import com.meli.ipgeolocalization.usecases.model.IpTrace;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DefaultIpTracerMapper implements  IpTraceMapper {

  @Override
  public IpTraceResponse mapResponse(String ip, IpTrace ipTrace, Country country, String currency,
      double distance) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date now = new Date();

    List<String> languages = ipTrace.getLanguages().stream()
        .map(item -> item.getName() + " ( " + item.getCode() + " )")
        .collect(Collectors.toList());

    return IpTraceResponseBuilder.aIpTraceResponseBuilder()
        .withIp(ip)
        .withCurrentDate(sdf.format(now))
        .withCountry(ipTrace.getCountryName())
        .withISOCode(ipTrace.getCountryCode())
        .withLanguages(languages)
        .withCurrency(currency)
        .withTimes(country.getTimes())
        .withEstimatedDistance(distance)
        .build();
  }
}
