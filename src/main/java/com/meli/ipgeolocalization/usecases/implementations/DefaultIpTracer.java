package com.meli.ipgeolocalization.usecases.implementations;

import com.meli.ipgeolocalization.delivery.rest.mappers.IpTraceMapper;
import com.meli.ipgeolocalization.delivery.rest.model.IpTraceResponse;
import com.meli.ipgeolocalization.repositories.interfaces.CountryRepository;
import com.meli.ipgeolocalization.repositories.interfaces.CurrencyRepository;
import com.meli.ipgeolocalization.repositories.interfaces.TracerRepository;
import com.meli.ipgeolocalization.usecases.interfaces.DistanceCalculator;
import com.meli.ipgeolocalization.usecases.interfaces.IpTracer;
import com.meli.ipgeolocalization.usecases.model.Country;
import com.meli.ipgeolocalization.usecases.model.GeoPt;
import com.meli.ipgeolocalization.usecases.model.IpTrace;
import com.meli.ipgeolocalization.utils.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DefaultIpTracer implements IpTracer {

  private final Logger logger = LoggerFactory.getLogger(DefaultIpTracer.class);
  private final TracerRepository tracerRepository;
  private final CountryRepository countryRepository;
  private final CurrencyRepository currencyRepository;
  private final DistanceCalculator distanceCalculator;
  private final IpTraceMapper mapper;

  @Value("${fixer.base_currency}")
  private String baseCurrency;

  private final GeoPt argentinaLocation = new GeoPt(-34.611778259277344, -58.41730880737305);

  public DefaultIpTracer(
      TracerRepository tracerRepository,
      CountryRepository countryRepository,
      CurrencyRepository currencyRepository,
      DistanceCalculator distanceCalculator,
      IpTraceMapper mapper) {
    this.tracerRepository = tracerRepository;
    this.countryRepository = countryRepository;
    this.currencyRepository = currencyRepository;
    this.distanceCalculator = distanceCalculator;
    this.mapper = mapper;
  }

  @Override
  @Cacheable(value = "trace", key = "#ip")
  public IpTraceResponse getTrace(String ip) {
    logger.info("getTrace service ip: {}", ip);

    IpTrace trace = tracerRepository.getTrace(ip);
    logger.info("Trace: {}", JacksonUtils.getPlainJsonJson(trace));

    Country country = countryRepository.getByISOCode(trace.getCountryCode());
    logger.info("Country: {}", JacksonUtils.getPlainJsonJson(country));

    String convertedCurrency = currencyRepository.convertCurrency(baseCurrency, country.getCurrency());
    logger.info("Converted currency: {}", convertedCurrency);

    GeoPt ipCountryLocation = new GeoPt(trace.getLatitude(), trace.getLongitude());
    double distance =  distanceCalculator.calculate(argentinaLocation, ipCountryLocation);
    logger.info("Distance between Argentina and origin ip country: {}", distance);

    return mapper.mapResponse(ip, trace, country, convertedCurrency, distance);
  }
}
