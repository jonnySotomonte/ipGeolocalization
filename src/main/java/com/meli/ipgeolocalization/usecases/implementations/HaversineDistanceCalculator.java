package com.meli.ipgeolocalization.usecases.implementations;

import com.meli.ipgeolocalization.usecases.interfaces.DistanceCalculator;
import com.meli.ipgeolocalization.usecases.model.GeoPt;
import org.springframework.stereotype.Service;

@Service
public class HaversineDistanceCalculator implements DistanceCalculator {

  /**
   * Haversine formula - adapted from https://www.movable-type.co.uk/scripts/latlong.html
   */
  @Override
  public double calculate(GeoPt originPoint, GeoPt targetPoint) {
    if ((originPoint.getLatitude() == 0 && originPoint.getLongitude() == 0) ||
        (targetPoint.getLatitude() == 0 && targetPoint.getLongitude() == 0)) {
      return 0;
    }

    double earthRadiusInMeters = 6378100;

    double originLatitudeInRadians = convertToRadians(originPoint.getLatitude());
    double originLongitudeInRadians = convertToRadians(originPoint.getLongitude());

    double targetLatitudeInRadians = convertToRadians(targetPoint.getLatitude());
    double targetLongitudeInRadians = convertToRadians(targetPoint.getLongitude());

    double deltaLatitude = targetLatitudeInRadians - originLatitudeInRadians;
    double deltaLongitude = targetLongitudeInRadians - originLongitudeInRadians;

    double distanceInStraightLine = hsin(deltaLatitude) +
        Math.cos(originLatitudeInRadians) *
            Math.cos(targetLatitudeInRadians) *
            hsin(deltaLongitude);
    double geographicDistanceInMeters = (2 * earthRadiusInMeters *
        Math.asin(Math.sqrt(distanceInStraightLine)));

    // Converting distance in meters to Km
    return (geographicDistanceInMeters / 1000);
  }

  private double convertToRadians(double coordinateValue) {
    return (coordinateValue * Math.PI) / 180;
  }

  private double hsin(double delta) {
    return Math.pow(Math.sin(delta / 2), 2);
  }
}
