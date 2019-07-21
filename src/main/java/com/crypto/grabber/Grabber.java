package com.crypto.grabber;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

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
}
