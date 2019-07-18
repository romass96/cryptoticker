package com.crypto;

import com.crypto.grabber.WazirxGrabber;
import com.crypto.model.CryptoCurrency;
import com.crypto.service.CryptoCurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public class Runner implements CommandLineRunner {
    protected static final Logger logger = LoggerFactory.getLogger(Runner.class);

    @Autowired
    private WazirxGrabber wazirxGrabber;

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    @Override
    public void run(String... args) throws Exception {
        List<CryptoCurrency> currencyList = cryptoCurrencyService.saveAll(wazirxGrabber.getAllAssets());
        for (CryptoCurrency cryptoCurrency: currencyList) {
            logger.info(cryptoCurrency.toString());
        }

    }
}
