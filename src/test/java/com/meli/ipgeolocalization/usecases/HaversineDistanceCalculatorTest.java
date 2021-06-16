package com.meli.ipgeolocalization.usecases;

import com.meli.ipgeolocalization.usecases.implementations.HaversineDistanceCalculator;
import com.meli.ipgeolocalization.usecases.interfaces.DistanceCalculator;
import com.meli.ipgeolocalization.usecases.model.GeoPt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HaversineDistanceCalculatorTest {

  private DistanceCalculator calculator;

  @Before
  public void setup() {
    calculator = new HaversineDistanceCalculator();
  }

  @Test
  public void calculate() {
    GeoPt argentinaPoint = new GeoPt(-34.611778259277344, -58.41730880737305);
    GeoPt unitedStatesPoint = new GeoPt(40.74156188964844, -73.88340759277344);
    double distance = calculator.calculate(argentinaPoint, unitedStatesPoint);
    Assert.assertEquals(8536.666, distance, 0.01);
  }

}
