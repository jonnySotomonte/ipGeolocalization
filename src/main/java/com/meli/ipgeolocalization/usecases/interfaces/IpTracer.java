package com.meli.ipgeolocalization.usecases.interfaces;

import com.meli.ipgeolocalization.delivery.rest.model.IpTraceResponse;

public interface IpTracer {

  IpTraceResponse getTrace(String ip);

}
