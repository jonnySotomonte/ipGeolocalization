package com.meli.ipgeolocalization.delivery.rest.mappers;

import com.meli.ipgeolocalization.delivery.rest.model.IpTraceResponse;
import com.meli.ipgeolocalization.usecases.model.Country;
import com.meli.ipgeolocalization.usecases.model.IpTrace;

public interface IpTraceMapper {
  IpTraceResponse mapResponse(String ip, IpTrace ipTrace, Country country, String currency, double distance);
}
