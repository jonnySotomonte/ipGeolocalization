package com.meli.ipgeolocalization.usecases.mappers;

import com.meli.ipgeolocalization.repositories.model.IpApiTracerResponse;
import com.meli.ipgeolocalization.repositories.model.RestCountriesResponse;
import com.meli.ipgeolocalization.usecases.model.Country;
import com.meli.ipgeolocalization.usecases.model.IpTrace;

public interface TraceMapper {

    IpTrace mapIpTrace(IpApiTracerResponse response);

    Country mapCountry(RestCountriesResponse response);
}
