package com.meli.ipgeolocalization.repositories.interfaces;

import com.meli.ipgeolocalization.usecases.model.Currency;

public interface CurrencyRepository {
  String convertCurrency(String from, Currency to);
}
