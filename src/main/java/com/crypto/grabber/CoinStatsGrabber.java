package com.crypto.grabber;

import com.crypto.model.CryptoCurrency;
import com.crypto.model.CryptoExchange;
import com.crypto.model.CryptoNews;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CoinStatsGrabber extends Grabber {
    private static final String API_URL = "https://api.coinstats.app/public/v1/";
    private static final String COINS_URL = API_URL + "coins";
    private static final String EXCHANGES_URL = API_URL + "exchanges";
    private static final String NEWS_URL = API_URL + "news";
    private static final int LIMIT = 500;


    public List<CryptoCurrency> getAllCoins() throws IOException {
        List<CryptoCurrency> cryptoCurrencies = new ArrayList<>();

        ObjectMapper objectMapper = getObjectMapper(CryptoCurrency.class, new CryptoCurrencyDeserializer());
        TypeReference typeReference = new TypeReference<List<CryptoCurrency>>() {};

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("limit", String.valueOf(LIMIT));

        List<CryptoCurrency> parsedCurrencies;
        int startIndex = 0;
        do {
            queryParams.put("skip", String.valueOf(startIndex));
            String finalJson = getFinalJsonNode(COINS_URL, queryParams, objectMapper, "coins").toString();
            parsedCurrencies = objectMapper.readValue(finalJson, typeReference);
            cryptoCurrencies.addAll(parsedCurrencies);
            startIndex += LIMIT;
        } while (!parsedCurrencies.isEmpty());

        return cryptoCurrencies;
    }

    public List<CryptoExchange> getAllExchanges() throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        Map<String, String> queryParams = Collections.emptyMap();

        String finalJson = getFinalJsonNode(EXCHANGES_URL, queryParams, objectMapper, "supportedExchanges").toString();
        List<String> exchanges = objectMapper.readValue(finalJson, new TypeReference<List<String>>(){});
        return exchanges.stream().map(CryptoExchange::new).collect(Collectors.toList());
    }

    public List<CryptoNews> getAllNews() throws IOException {
        List<CryptoNews> cryptoNews = new ArrayList<>();

        ObjectMapper objectMapper = getObjectMapper(CryptoNews.class, new CryptoNewsDeserializer());
        TypeReference typeReference = new TypeReference<List<CryptoNews>>() {};

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("limit", String.valueOf(LIMIT));

        List<CryptoNews> parsedNews;
        int startIndex = 0;
        do {
            queryParams.put("skip", String.valueOf(startIndex));
            String finalJson = getFinalJsonNode(NEWS_URL, queryParams, objectMapper, "news").toString();
            parsedNews = objectMapper.readValue(finalJson, typeReference);
            cryptoNews.addAll(parsedNews);
            startIndex += LIMIT;
        } while (!parsedNews.isEmpty() || startIndex < 100);
        return cryptoNews;
    }

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

        cryptoCurrency.setName(currencyNode.get("name").textValue());
        cryptoCurrency.setSymbol(currencyNode.get("symbol").textValue());
        cryptoCurrency.setCoinstatsId(currencyNode.get("id").textValue());
        cryptoCurrency.setPriceUSD(currencyNode.get("price").doubleValue());
        cryptoCurrency.setPriceBTC(currencyNode.get("priceBtc").doubleValue());
        cryptoCurrency.setIcon(currencyNode.get("icon").textValue());
        if (currencyNode.get("volume") != null) {
            cryptoCurrency.setDayVolume(currencyNode.get("volume").doubleValue());
        }
        cryptoCurrency.setCapitalization(currencyNode.get("marketCap").doubleValue());
        if (currencyNode.get("availableSupply") != null) {
            cryptoCurrency.setAvailableSupply(currencyNode.get("availableSupply").longValue());
        }
        if (currencyNode.get("totalSupply") != null) {
            cryptoCurrency.setTotalSupply(currencyNode.get("totalSupply").longValue());
        }
        cryptoCurrency.setPriceChange1h(currencyNode.get("priceChange1h").doubleValue());
        cryptoCurrency.setPriceChange1d(currencyNode.get("priceChange1d").doubleValue());
        cryptoCurrency.setPriceChange1w(currencyNode.get("priceChange1w").doubleValue());
        if (currencyNode.get("websiteUrl") != null) {
            cryptoCurrency.setWebsiteUrl(currencyNode.get("websiteUrl").textValue());
        }

        if (currencyNode.get("redditUrl") != null) {
            cryptoCurrency.setRedditUrl(currencyNode.get("redditUrl").textValue());
        }

        if (currencyNode.get("twitterUrl") != null) {
            cryptoCurrency.setTwitterUrl(currencyNode.get("twitterUrl").textValue());
        }

        if (currencyNode.get("contractAddress") != null) {
            cryptoCurrency.setContractAddress(currencyNode.get("contractAddress").textValue());
        }

        return cryptoCurrency;
    }

}

class CryptoNewsDeserializer extends StdDeserializer<CryptoNews> {


    public CryptoNewsDeserializer() {
        this(null);
    }

    protected CryptoNewsDeserializer(Class<CryptoNews> t) {
        super(t);
    }
    @Override
    public CryptoNews deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        CryptoNews cryptoNews = new CryptoNews();
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode currencyNode = objectCodec.readTree(jsonParser);

        cryptoNews.setId(currencyNode.get("id").textValue());
        cryptoNews.setFeedDate(new Timestamp(currencyNode.get("feedDate").longValue()));
        cryptoNews.setSource(currencyNode.get("source").textValue());
        cryptoNews.setTitle(currencyNode.get("title").textValue());
        cryptoNews.setImgUrl(currencyNode.get("imgURL").textValue());
        cryptoNews.setLink(currencyNode.get("link").textValue());

        return cryptoNews;
    }

}
