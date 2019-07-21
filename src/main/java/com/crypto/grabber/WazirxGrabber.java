package com.crypto.grabber;

import com.crypto.model.Ticker;
import com.crypto.service.CryptoCurrencyService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

@Component
public class WazirxGrabber extends Grabber {
    private static final String API_URL = "https://api.wazirx.com/api/v2/";
    private static final String ASSETS_URL = API_URL + "market-status";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    public WazirxGrabber() {
        super("https://api.wazirx.com/api/v2/tickers");
    }

//    @Scheduled(fixedRate = 100000)
    public void execute() throws IOException {
        logger.info("Executing wazirx grabber");
        HttpEntity entity = getEntityWithHeaders();

        HttpEntity<String> response = restTemplate.exchange(tickerUrl, HttpMethod.GET, entity, String.class);
        Map<String, WazirxJsonResponse> map = objectMapper.readValue(response.getBody(), getTypeReference());
        map.values().stream().map(jsonResponse -> {
            Ticker ticker = new Ticker();
            ticker.setBaseCurrency(cryptoCurrencyService.findByShortName(jsonResponse.base_unit));
            ticker.setQuoteCurrency(cryptoCurrencyService.findByShortName(jsonResponse.quote_unit));
            ticker.setPrice(jsonResponse.last);
            return ticker;
        }).forEach(ticker -> logger.info(ticker.toString()));

    }

    private TypeReference<Map<String, WazirxJsonResponse>> getTypeReference() {
        return new TypeReference<Map<String, WazirxJsonResponse>>() {};
    }

}

class WazirxJsonResponse {
    public String base_unit;
    public String quote_unit;
    public double low;
    public double high;
    public double last;
    public String type;
    public double open;
    public double volume;
    public double sell;
    public double buy;
    public String name;
    public Timestamp at;
}

class Asset {
    public String type;
    public String name;
    public double withdrawFee;
    public double minWithdrawAmount;
    public double maxWithdrawAmount;
    public double minDepositAmount;
    public int confirmations;
    public String deposit;
    public String withdrawal;
}
