package com.meli.ipgeolocalization;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpClientConfiguration {

  @Bean
  @Qualifier("meliRestTemplate")
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
