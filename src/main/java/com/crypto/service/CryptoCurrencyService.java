package com.crypto.service;

import com.crypto.model.CryptoCurrency;
import com.crypto.repository.CryptoCurrencyRepository;
import com.crypto.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CryptoCurrencyService {

    private List<CryptoCurrency> cryptoCurrencies = new ArrayList<>();

    @Autowired
    private CryptoCurrencyRepository cryptoCurrencyRepository;

    public List<CryptoCurrency> saveAll(List<CryptoCurrency> cryptoCurrencies) {
      return Utils.iterableToList(cryptoCurrencyRepository.saveAll(cryptoCurrencies));
    }

    public List<CryptoCurrency> findAll() {
        return Utils.iterableToList(cryptoCurrencyRepository.findAll());
    }

    public List<CryptoCurrency> getAllCryptoCurrencies() {
        if (cryptoCurrencies.isEmpty()) {
            cryptoCurrencies = findAll();
        }

        return cryptoCurrencies;
    }

    public CryptoCurrency findByShortName(String shortName) {
        return getAllCryptoCurrencies()
                .stream()
                .filter(cryptoCurrency -> cryptoCurrency.getShortName().equalsIgnoreCase(shortName))
                .findAny()
                .orElse(null);
    }

}
