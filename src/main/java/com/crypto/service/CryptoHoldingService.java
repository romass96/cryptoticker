package com.crypto.service;

import com.crypto.model.CryptoHolding;
import com.crypto.repository.CryptoHoldingRepository;
import com.crypto.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoHoldingService {

    @Autowired
    private CryptoHoldingRepository cryptoHoldingRepository;

    public List<CryptoHolding> findAll() {
        return Utils.iterableToList(cryptoHoldingRepository.findAll());
    }

    public CryptoHolding add(CryptoHolding cryptoHolding) {
        return cryptoHoldingRepository.save(cryptoHolding);
    }

    public CryptoHolding update(CryptoHolding cryptoHolding) {
        return cryptoHoldingRepository.save(cryptoHolding);
    }
}
