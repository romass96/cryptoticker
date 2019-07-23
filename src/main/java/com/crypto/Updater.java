package com.crypto;

import com.crypto.grabber.CoinStatsGrabber;
import com.crypto.model.CryptoCurrency;
import com.crypto.service.CryptoCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class Updater {

    @Autowired
    private CoinStatsGrabber coinStatsGrabber;

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    @Scheduled(cron = "0 0 * ? * *")
    public void updateCoinStatsCoins() throws IOException {
        List<CryptoCurrency> currencyList = coinStatsGrabber.getAllCoins();
        currencyList.forEach(cryptoCurrency -> {
            CryptoCurrency byId = cryptoCurrencyService.findByCoinStatsId(cryptoCurrency.getCoinstatsId());
            if (byId != null) {
                cryptoCurrency.setId(byId.getId());
            }
            cryptoCurrencyService.save(cryptoCurrency);
        });
    }
}
