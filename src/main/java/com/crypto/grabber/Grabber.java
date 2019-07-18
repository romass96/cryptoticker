package com.crypto.grabber;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public abstract class Grabber {
    protected static final Logger logger = LoggerFactory.getLogger(Grabber.class);

    protected RestTemplate restTemplate = new RestTemplate();
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected String tickerUrl;

    public Grabber(String tickerUrl) {
        this.tickerUrl = tickerUrl;
    }

    public abstract void execute() throws IOException;

}
