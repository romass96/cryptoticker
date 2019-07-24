package com.crypto;

import com.crypto.grabber.CoinMarketCapGrabber;
import com.crypto.grabber.CoinStatsGrabber;
import com.crypto.model.CryptoCurrency;
import com.crypto.service.CryptoCurrencyService;
import com.crypto.service.CryptoExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {
    protected static final Logger logger = LoggerFactory.getLogger(Runner.class);

    @Autowired
    private CoinMarketCapGrabber coinMarketCapGrabber;

    @Autowired
    private CoinStatsGrabber coinStatsGrabber;

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    @Autowired
    private CryptoExchangeService cryptoExchangeService;

    @Override
    public void run(String... args) throws Exception {
//        List<CryptoCurrency> currencyList = cryptoCurrencyService.updateAll(coinMarketCapGrabber.getAllLatestCryptoCurrencies());
        List<CryptoCurrency> currencyList = cryptoCurrencyService.saveAll(coinStatsGrabber.getAllCoins());
        logger.info(currencyList.get(0).toString());

    }
}
