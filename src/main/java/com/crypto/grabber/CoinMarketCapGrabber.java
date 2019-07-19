package com.crypto.grabber;

import com.crypto.model.CryptoCurrency;
import com.crypto.service.CryptoCurrencyService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class CoinMarketCapGrabber extends Grabber{

    private static final String LATEST_CRYPTO_CURRENCIES_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
    private static final String CRYPTO_METADATA_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/info";
    private static final String API_KEY = "b61a3de2-f427-484e-a68d-6add72f6d167";
    private static final String CONVERTION_CURRENCY = "USD";

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    public List<CryptoCurrency> getAllLatestCryptoCurrencies() throws IOException {
        HttpEntity entity = getEntityWithHeaders();
        ObjectMapper objectMapper = getObjectMapper(CryptoCurrency.class, new CryptoCurrencyDeserializer());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(LATEST_CRYPTO_CURRENCIES_URL)
                .queryParam("limit", 100)
                .queryParam("convert", CONVERTION_CURRENCY)
                .queryParam("CMC_PRO_API_KEY", API_KEY);

        HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
        String finalJson = objectMapper.readTree(response.getBody()).get("data").toString();
        return getCryptoCurrencyMetadata(objectMapper.readValue(finalJson, new TypeReference<List<CryptoCurrency>>() {}));
    }

    public List<CryptoCurrency> getCryptoCurrencyMetadata(List<CryptoCurrency> cryptoCurrencies) throws IOException {
        HttpEntity entity = getEntityWithHeaders();
        ObjectMapper objectMapper = getObjectMapper(CryptoCurrency.class, new CryptoCurrencyMetadataDeserializer());

        String currencyShortNames = cryptoCurrencies
                .stream()
                .map(CryptoCurrency::getShortName)
                .collect(Collectors.joining(","));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(CRYPTO_METADATA_URL)
                .queryParam("symbol", currencyShortNames)
                .queryParam("CMC_PRO_API_KEY", API_KEY);

        HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
        JsonNode jsonNode = objectMapper.readTree(response.getBody()).get("data");
        cryptoCurrencies.stream().forEach(setMetadata(jsonNode, objectMapper));
        return cryptoCurrencies;
    }

    private Consumer<CryptoCurrency> setMetadata(JsonNode jsonNode, ObjectMapper objectMapper) {
        return cryptoCurrency -> {
            try {
                String currencyJson = jsonNode.get(cryptoCurrency.getShortName()).toString();
                cryptoCurrency.merge(objectMapper.readValue(currencyJson, new TypeReference<CryptoCurrency>() {}));

            } catch (IOException e) {
                logger.error("Can't to get metadata for " + cryptoCurrency.getShortName());
                e.printStackTrace();
            }
        };
    }

    protected ObjectMapper getObjectMapper(Class deserializationClass, StdDeserializer deserializer) {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(deserializationClass, deserializer);
        objectMapper.registerModule(module);
        return objectMapper;
    }

    class CryptoCurrencyDeserializer extends StdDeserializer<CryptoCurrency> {


        public CryptoCurrencyDeserializer() {
            this(null);
        }

        protected CryptoCurrencyDeserializer(Class<CryptoCurrency> t) {
            super(t);
        }
        @Override
        public CryptoCurrency deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            CryptoCurrency cryptoCurrency = new CryptoCurrency();
            ObjectCodec objectCodec = jsonParser.getCodec();
            JsonNode currencyNode = objectCodec.readTree(jsonParser);

            cryptoCurrency.setFullName(currencyNode.get("name").textValue());
            cryptoCurrency.setShortName(currencyNode.get("symbol").textValue());
            cryptoCurrency.setMaxSupply(currencyNode.get("max_supply").longValue());
            cryptoCurrency.setCirculatingSupply(currencyNode.get("circulating_supply").longValue());

            JsonNode quoteNode = currencyNode.get("quote").get(CONVERTION_CURRENCY);
            cryptoCurrency.setCapitalization(quoteNode.get("market_cap").doubleValue());
            cryptoCurrency.setPrice(quoteNode.get("price").doubleValue());
            cryptoCurrency.setDayVolume(quoteNode.get("volume_24h").doubleValue());
            cryptoCurrency.setPercentChange1h(quoteNode.get("percent_change_1h").doubleValue());
            cryptoCurrency.setPercentChange24h(quoteNode.get("percent_change_24h").doubleValue());
            cryptoCurrency.setPercentChange7d(quoteNode.get("percent_change_7d").doubleValue());

            return cryptoCurrency;
        }
    }

    class CryptoCurrencyMetadataDeserializer extends StdDeserializer<CryptoCurrency> {


        public CryptoCurrencyMetadataDeserializer() {
            this(null);
        }

        protected CryptoCurrencyMetadataDeserializer(Class<CryptoCurrency> t) {
            super(t);
        }
        @Override
        public CryptoCurrency deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            CryptoCurrency cryptoCurrency = new CryptoCurrency();
            ObjectCodec objectCodec = jsonParser.getCodec();
            JsonNode currencyNode = objectCodec.readTree(jsonParser);

            cryptoCurrency.setLogoUrl(currencyNode.get("logo").textValue());
            cryptoCurrency.setDescription(currencyNode.get("description").textValue());

            return cryptoCurrency;
        }
    }
}
