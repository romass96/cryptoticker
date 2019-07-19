package com.crypto.grabber;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

public class Grabber {
    protected static final Logger logger = LoggerFactory.getLogger(Grabber.class);

    protected RestTemplate restTemplate = new RestTemplate();
    protected ObjectMapper objectMapper = getObjectMapper();
    protected String tickerUrl;

    public Grabber() {

    }

    public Grabber(String tickerUrl) {
        this.tickerUrl = tickerUrl;
    }

    protected HttpEntity getEntityWithHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        return new HttpEntity(headers);
    }

    protected ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
