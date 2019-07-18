package com.crypto.service;

import com.crypto.repository.CryptoExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CryptoExchangeService {

    @Autowired
    private CryptoExchangeRepository cryptoExchangeRepository;

}
