package com.meli.ipgeolocalization.utils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

public class JacksonUtilsTest {

  @Test
  public void getPlainJsonJson_Fail() {
    Object mockItem = mock(Object.class);
    when(mockItem.toString()).thenReturn(mockItem.getClass().getName());
    String objectAsString = JacksonUtils.getPlainJsonJson(mockItem);
    Assert.assertEquals("", objectAsString);
  }

}
