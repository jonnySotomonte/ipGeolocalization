package com.meli.ipgeolocalization.usecases.mappers;

import com.meli.ipgeolocalization.repositories.model.IpApiTracerResponse;
import com.meli.ipgeolocalization.repositories.model.RestCountriesResponse;
import com.meli.ipgeolocalization.usecases.model.Country;
import com.meli.ipgeolocalization.usecases.model.Country.CountryBuilder;
import com.meli.ipgeolocalization.usecases.model.Currency;
import com.meli.ipgeolocalization.usecases.model.Currency.CurrencyBuilder;
import com.meli.ipgeolocalization.usecases.model.IpTrace;
import com.meli.ipgeolocalization.usecases.model.IpTrace.IpTraceBuilder;
import com.meli.ipgeolocalization.usecases.model.Language;
import com.meli.ipgeolocalization.usecases.model.Language.LanguageBuilder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DefaultTraceMapper implements TraceMapper {

  @Override
  public IpTrace mapIpTrace(IpApiTracerResponse response) {

    List<Language> languages = response.getLocation().getLanguages()
        .stream().map(countryLanguage -> LanguageBuilder.aLanguageBuilder()
        .withCode(countryLanguage.getCode())
        .withName(countryLanguage.getName()).build())
        .collect(Collectors.toList());

    return IpTraceBuilder.aIpTraceBuilder()
        .withCountryCode(response.getCountryCode())
        .withCountryName(response.getCountryName())
        .withLatitude(response.getLatitude())
        .withLongitude(response.getLongitude())
        .withLanguages(languages)
        .build();
  }

  @Override
  public Country mapCountry(RestCountriesResponse response) {
    final Date now = new Date();

    List<String> times = response.getTimezones().stream().map(item -> {
      String timezone = item.replace("UTC", "GMT");
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      sdf.setTimeZone(TimeZone.getTimeZone(timezone));
      return sdf.format(now) + " ( " + timezone + " )";
    }).collect(Collectors.toList());

    Currency currency = CurrencyBuilder.aCurrencyBuilder()
        .withCode(response.getCurrencies().get(0).getCode())
        .withName(response.getCurrencies().get(0).getName()).build();

    return CountryBuilder.aCountryBuilder()
        .withTimes(times)
        .withCurrency(currency)
        .build();
  }
}
