package com.meli.ipgeolocalization.usecases.interfaces;

import com.meli.ipgeolocalization.usecases.model.GeoPt;

public interface DistanceCalculator {

  double calculate(GeoPt originPoint, GeoPt targetPoint);

}
