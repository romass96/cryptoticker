package com.crypto.service;

import com.crypto.model.CryptoCurrency;
import com.crypto.model.CryptoExchange;
import com.crypto.repository.CryptoExchangeRepository;
import com.crypto.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CryptoExchangeService {

    @Autowired
    private CryptoExchangeRepository cryptoExchangeRepository;

    public List<CryptoExchange> saveAll(List<CryptoExchange> cryptoExchanges) {
        return Utils.iterableToList(cryptoExchangeRepository.saveAll(cryptoExchanges));
    }

    public CryptoExchange save(CryptoExchange cryptoExchange) {
        return cryptoExchangeRepository.save(cryptoExchange);
    }

    public List<CryptoExchange> updateAll(List<CryptoExchange> cryptoExchanges) {
        cryptoExchanges.forEach(cryptoExchange -> {
            CryptoExchange byName = findByName(cryptoExchange.getName());
            if (byName != null) {
                cryptoExchange.setId(byName.getId());
            }
            save(cryptoExchange);
        });
        return findAll();
    }

    public List<CryptoExchange> findAll() {
        return Utils.iterableToList(cryptoExchangeRepository.findAll());
    }

    public CryptoExchange findByName(String name) {
        return cryptoExchangeRepository.findByName(name);
    }

}
