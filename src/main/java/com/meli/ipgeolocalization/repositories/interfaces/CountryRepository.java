package com.meli.ipgeolocalization.repositories.interfaces;

import com.meli.ipgeolocalization.usecases.model.Country;

public interface CountryRepository {

  Country getByISOCode(String code);

}
