package com.crypto.controller;

import com.crypto.grabber.CoinStatsGrabber;
import com.crypto.service.CryptoCurrencyService;
import com.crypto.service.CryptoExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class MainController {

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    @Autowired
    private CoinStatsGrabber coinStatsGrabber;

    @Autowired
    private CryptoExchangeService cryptoExchangeService;

    @GetMapping({"/", "/cryptocurrencies"})
    public String currencyPage(Model model) {
        model.addAttribute("currencies", cryptoCurrencyService.findAll());
        return "currencyList";
    }

    @GetMapping("/cryptoexchanges")
    public String exchangePage(Model model) {
        model.addAttribute("exchanges", cryptoExchangeService.findAll());
        return "exchangeList";
    }

    @GetMapping("/cryptocurrency/{id}")
    public String currencyInfo(Model model, @PathVariable Long id) {
        model.addAttribute("currency", cryptoCurrencyService.findById(id));
        return "currencyInfo";
    }

    @GetMapping("/news")
    public String news(Model model) throws IOException {
        model.addAttribute("news", coinStatsGrabber.getAllNews());
        return "news";
    }
}
