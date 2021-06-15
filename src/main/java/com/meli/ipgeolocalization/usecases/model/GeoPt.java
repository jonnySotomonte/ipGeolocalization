package com.meli.ipgeolocalization.usecases.model;

public class GeoPt {

  private final double latitude;
  private final double longitude;

  public GeoPt(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }
}
