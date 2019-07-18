package com.crypto.grabber;

import com.crypto.model.CryptoCurrency;
import com.crypto.model.Ticker;
import com.crypto.service.CryptoCurrencyService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WazirxGrabber extends Grabber {
    private static final String assetsUrl = "https://api.wazirx.com/api/v2/market-status";

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    public WazirxGrabber() {
        super("https://api.wazirx.com/api/v2/tickers");
    }

    @Override
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

    public List<CryptoCurrency> getAllAssets() throws IOException {
        HttpEntity entity = getEntityWithHeaders();
        HttpEntity<String> response = restTemplate.exchange(assetsUrl, HttpMethod.GET, entity, String.class);
        JsonNode tree = objectMapper.readTree(response.getBody());
        ObjectReader assetReader = objectMapper.readerFor(new TypeReference<List<Asset>>() {});
        List<Asset> assets = assetReader.readValue(tree.get("assets"));
        return assets.stream().map(asset -> {
            CryptoCurrency cryptoCurrency = new CryptoCurrency();
            cryptoCurrency.setFullName(asset.name);
            cryptoCurrency.setShortName(asset.type.toUpperCase());
            return cryptoCurrency;
        }).collect(Collectors.toList());

    }

    private TypeReference<Map<String, WazirxJsonResponse>> getTypeReference() {
        return new TypeReference<Map<String, WazirxJsonResponse>>() {};
    }

    private HttpEntity getEntityWithHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        return new HttpEntity(headers);
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
