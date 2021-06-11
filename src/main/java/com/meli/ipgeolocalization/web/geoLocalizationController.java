package com.meli.ipgeolocalization.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class geoLocalizationController {

  @GetMapping("/")
  public ResponseEntity<String> health() {
    return new ResponseEntity<>("Todo OK !!!!", HttpStatus.OK);
  }

}
