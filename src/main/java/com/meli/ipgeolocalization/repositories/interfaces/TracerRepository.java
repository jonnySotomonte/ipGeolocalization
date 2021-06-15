package com.meli.ipgeolocalization.repositories.interfaces;

import com.meli.ipgeolocalization.usecases.model.IpTrace;

public interface TracerRepository {

  IpTrace getTrace(String ip);

}
