package com.crypto.service;

import com.crypto.model.CryptoCurrency;
import com.crypto.repository.CryptoCurrencyRepository;
import com.crypto.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CryptoCurrencyService {

    @Autowired
    private CryptoCurrencyRepository cryptoCurrencyRepository;

    public List<CryptoCurrency> saveAll(List<CryptoCurrency> cryptoCurrencies) {
        return Utils.iterableToList(cryptoCurrencyRepository.saveAll(cryptoCurrencies));
    }

    public CryptoCurrency save(CryptoCurrency cryptoCurrency) {
        return cryptoCurrencyRepository.save(cryptoCurrency);
    }

    public CryptoCurrency findById(Long id) {
        return cryptoCurrencyRepository.findById(id).orElse(null);
    }

    public List<CryptoCurrency> findAll() {
        return Utils.iterableToList(cryptoCurrencyRepository.findAll());
    }

    public CryptoCurrency findBySymbol(String symbol) {
        return cryptoCurrencyRepository.findBySymbol(symbol);
    }

    public CryptoCurrency findByCoinMarketCapId(Long id) {
        return cryptoCurrencyRepository.findByCoinMarketCapId(id);
    }

    public CryptoCurrency findByCoinStatsId(String coinStatsId) {
        return cryptoCurrencyRepository.findByCoinstatsId(coinStatsId);
    }



}
