package com.crypto.grabber;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

public class Grabber {
    protected static final Logger logger = LoggerFactory.getLogger(Grabber.class);
    protected static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";

    protected RestTemplate restTemplate = new RestTemplate();
    protected String tickerUrl;

    public Grabber() {

    }

    public Grabber(String tickerUrl) {
        this.tickerUrl = tickerUrl;
    }

    protected HttpEntity getEntityWithHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.USER_AGENT, USER_AGENT);
        return new HttpEntity(headers);
    }

    protected ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    protected ObjectMapper getObjectMapper(Class deserializationClass, StdDeserializer deserializer) {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(deserializationClass, deserializer);
        objectMapper.registerModule(module);
        return objectMapper;
    }

    protected JsonNode getFinalJsonNode(String url, Map<String, String> queryParams, ObjectMapper objectMapper, String mainNodeName) throws IOException {
        HttpEntity entity = getEntityWithHeaders();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        queryParams.forEach(builder::queryParam);
        logger.debug("Performing request to " + builder.toUriString());
        HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
        return objectMapper.readTree(response.getBody()).get(mainNodeName);
    }
}
