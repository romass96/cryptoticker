package com.crypto.grabber;

import com.crypto.model.CryptoCurrency;
import com.crypto.model.CryptoExchange;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CoinMarketCapGrabber extends Grabber{
    private static final String API_URL = "https://pro-api.coinmarketcap.com/v1/";
    private static final String LATEST_CRYPTO_CURRENCIES_URL = API_URL + "cryptocurrency/listings/latest";
    private static final String LATEST_CRYPTO_EXCHANGES_URL = API_URL + "exchange/listings/latest";
    private static final String CURRENCY_METADATA_URL = API_URL + "cryptocurrency/info";
    private static final String EXCHANGE_METADATA_URL = API_URL + "exchange/info";
    private static final String API_KEY = "b61a3de2-f427-484e-a68d-6add72f6d167";
    private static final String CONVERT_CURRENCY = "USD";
    private static final int LIMIT = 500;

//    @Scheduled(cron = "0 0 0 * * ?")
//    public List<CryptoCurrency> getAllLatestCryptoCurrencies() throws IOException {
//        List<CryptoCurrency> cryptoCurrencies = new ArrayList<>();
//
//        ObjectMapper objectMapper = getObjectMapper(CryptoCurrency.class, new CryptoCurrencyDeserializer());
//        TypeReference typeReference = new TypeReference<List<CryptoCurrency>>() {};
//
//        Map<String, String> queryParams = new HashMap<>();
//        queryParams.put("limit", String.valueOf(LIMIT));
//        queryParams.put("convert", CONVERT_CURRENCY);
//
//        for (int startIndex = 1; ;startIndex += LIMIT) {
//            queryParams.put("start", String.valueOf(startIndex));
//
//            String finalJson = getFinalJsonNode(LATEST_CRYPTO_CURRENCIES_URL, queryParams, objectMapper).toString();
//            List<CryptoCurrency> parsedCurrencies = objectMapper.readValue(finalJson, typeReference);
//            if (parsedCurrencies.isEmpty()) {
//                break;
//            }
//            cryptoCurrencies.addAll(getCryptoCurrencyMetadata(parsedCurrencies));
//        }
//
//        return cryptoCurrencies;
//    }
//
//    public List<CryptoExchange> getAllLatestCryptoExchanges() throws IOException {
//        Map<String, String> queryParams = new HashMap<>();
//        queryParams.put("limit", "100");
//        queryParams.put("convert", CONVERT_CURRENCY);
//        ObjectMapper objectMapper = getObjectMapper(CryptoExchange.class, new CryptoExchangeDeserializer());
//        String finalJson = getFinalJsonNode(LATEST_CRYPTO_EXCHANGES_URL, queryParams, objectMapper).toString();
//        return getCryptoExchangeMetadata(objectMapper.readValue(finalJson, new TypeReference<List<CryptoExchange>>() {}));
//    }
//
//    private List<CryptoCurrency> getCryptoCurrencyMetadata(List<CryptoCurrency> cryptoCurrencies) throws IOException {
//        String coinMarketCapIds = cryptoCurrencies
//                .stream()
//                .map(cryptoCurrency -> cryptoCurrency.getCoinMarketCapId().toString())
//                .collect(Collectors.joining(","));
//
//        Map<String, String> queryParams = new HashMap<>();
//        queryParams.put("id", coinMarketCapIds);
//
//        ObjectMapper objectMapper = getObjectMapper(CryptoCurrency.class, new CryptoCurrencyMetadataDeserializer());
//        JsonNode jsonNode = getFinalJsonNode(CURRENCY_METADATA_URL, queryParams, objectMapper);
//        cryptoCurrencies.forEach(cryptoCurrency -> {
//            try {
//                String currencyJson = jsonNode.get(cryptoCurrency.getCoinMarketCapId().toString()).toString();
//                cryptoCurrency.merge(objectMapper.readValue(currencyJson, new TypeReference<CryptoCurrency>() {}));
//            } catch (IOException e) {
//                logger.error("Can't to get metadata for " + cryptoCurrency.getSymbol(), e);
//            }
//        });
//        return cryptoCurrencies;
//    }
//
//    private List<CryptoExchange> getCryptoExchangeMetadata(List<CryptoExchange> cryptoExchanges) throws IOException {
//        String exchangeNames = cryptoExchanges
//                .stream()
//                .map(cryptoExchange -> cryptoExchange.getName().toLowerCase())
//                .collect(Collectors.joining(","));
//
//        Map<String, String> queryParams = new HashMap<>();
//        queryParams.put("slug", exchangeNames);
//
//        ObjectMapper objectMapper = getObjectMapper(CryptoExchange.class, new CryptoExchangeMetadataDeserializer());
//        JsonNode jsonNode = getFinalJsonNode(EXCHANGE_METADATA_URL, queryParams, objectMapper);
//        cryptoExchanges.forEach(cryptoExchange -> {
//            try {
//                String exchangeJson = jsonNode.get(cryptoExchange.getName().toLowerCase()).toString();
//                cryptoExchange.merge(objectMapper.readValue(exchangeJson, new TypeReference<CryptoExchange>() {}));
//            } catch (IOException e) {
//                logger.error("Can't to get metadata for " + cryptoExchange.getName(), e);
//            }
//        });
//        return cryptoExchanges;
//    }
//
//
    private JsonNode getFinalJsonNode(String url, Map<String, String> queryParams, ObjectMapper objectMapper) throws IOException {
        return getFinalJsonNode(url, queryParams, objectMapper, "data");
    }
//
//    @Override
//    protected HttpEntity getEntityWithHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("X-CMC_PRO_API_KEY", API_KEY);
//        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
//        headers.add(HttpHeaders.USER_AGENT, USER_AGENT);
//        return new HttpEntity(headers);
//    }
//
//    class CryptoCurrencyDeserializer extends StdDeserializer<CryptoCurrency> {
//
//
//        public CryptoCurrencyDeserializer() {
//            this(null);
//        }
//
//        protected CryptoCurrencyDeserializer(Class<CryptoCurrency> t) {
//            super(t);
//        }
//        @Override
//        public CryptoCurrency deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
//            CryptoCurrency cryptoCurrency = new CryptoCurrency();
//            ObjectCodec objectCodec = jsonParser.getCodec();
//            JsonNode currencyNode = objectCodec.readTree(jsonParser);
//
//            cryptoCurrency.setName(currencyNode.get("name").textValue());
//            cryptoCurrency.setSymbol(currencyNode.get("symbol").textValue());
//            cryptoCurrency.setTotalSupply(currencyNode.get("max_supply").longValue());
//            cryptoCurrency.setCirculatingSupply(currencyNode.get("circulating_supply").longValue());
//            cryptoCurrency.setCoinMarketCapId(currencyNode.get("id").longValue());
//
//            JsonNode quoteNode = currencyNode.get("quote").get(CONVERT_CURRENCY);
//            cryptoCurrency.setCapitalization(quoteNode.get("market_cap").doubleValue());
//            cryptoCurrency.setPriceUSD(quoteNode.get("price").doubleValue());
//            cryptoCurrency.setDayVolume(quoteNode.get("volume_24h").doubleValue());
//            cryptoCurrency.setPriceChange1h(quoteNode.get("percent_change_1h").doubleValue());
//            cryptoCurrency.setPriceChange1d(quoteNode.get("percent_change_24h").doubleValue());
//            cryptoCurrency.setPriceChange1w(quoteNode.get("percent_change_7d").doubleValue());
//
//            return cryptoCurrency;
//        }
//    }
//
//    class CryptoCurrencyMetadataDeserializer extends StdDeserializer<CryptoCurrency> {
//
//
//        public CryptoCurrencyMetadataDeserializer() {
//            this(null);
//        }
//
//        protected CryptoCurrencyMetadataDeserializer(Class<CryptoCurrency> t) {
//            super(t);
//        }
//        @Override
//        public CryptoCurrency deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
//            CryptoCurrency cryptoCurrency = new CryptoCurrency();
//            ObjectCodec objectCodec = jsonParser.getCodec();
//            JsonNode currencyNode = objectCodec.readTree(jsonParser);
//
//            cryptoCurrency.setIcon(currencyNode.get("logo").textValue());
//            cryptoCurrency.setDescription(currencyNode.get("description").textValue());
//
//            return cryptoCurrency;
//        }
//    }
//
//    class CryptoExchangeDeserializer extends StdDeserializer<CryptoExchange> {
//
//
//        public CryptoExchangeDeserializer() {
//            this(null);
//        }
//
//        protected CryptoExchangeDeserializer(Class<CryptoExchange> t) {
//            super(t);
//        }
//        @Override
//        public CryptoExchange deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
//            CryptoExchange cryptoExchange = new CryptoExchange();
//            ObjectCodec objectCodec = jsonParser.getCodec();
//            JsonNode currencyNode = objectCodec.readTree(jsonParser);
//
//            cryptoExchange.setName(currencyNode.get("name").textValue());
//            return cryptoExchange;
//        }
//    }
//
//    class CryptoExchangeMetadataDeserializer extends StdDeserializer<CryptoExchange> {
//
//
//        public CryptoExchangeMetadataDeserializer() {
//            this(null);
//        }
//
//        protected CryptoExchangeMetadataDeserializer(Class<CryptoExchange> t) {
//            super(t);
//        }
//        @Override
//        public CryptoExchange deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
//            CryptoExchange cryptoExchange = new CryptoExchange();
//            ObjectCodec objectCodec = jsonParser.getCodec();
//            JsonNode exchangeNode = objectCodec.readTree(jsonParser);
//
//            cryptoExchange.setWebSite(exchangeNode.get("urls").get("website").get(0).textValue());
//            cryptoExchange.setDateLaunched(new Date(exchangeNode.get("date_launched").asLong()));
//            cryptoExchange.setLogoUrl(exchangeNode.get("logo").textValue());
//
//            return cryptoExchange;
//        }
//    }
}
