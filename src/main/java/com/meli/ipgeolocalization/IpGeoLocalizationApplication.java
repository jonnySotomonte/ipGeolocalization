package com.meli.ipgeolocalization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class IpGeoLocalizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpGeoLocalizationApplication.class, args);
	}

}
