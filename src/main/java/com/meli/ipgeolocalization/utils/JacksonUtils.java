package com.meli.ipgeolocalization.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JacksonUtils {

  public static String getPlainJsonJson(Object object) {
    Logger logger = LoggerFactory.getLogger(JacksonUtils.class);
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      logger.info("There was an error converting the object to json string");
      return "";
    }
  }

}
