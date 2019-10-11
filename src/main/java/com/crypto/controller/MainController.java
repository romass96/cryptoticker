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
    private static final String ACTIVE_PAGE_CLASS = "active";

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    @Autowired
    private CoinStatsGrabber coinStatsGrabber;

    @Autowired
    private CryptoExchangeService cryptoExchangeService;

    @GetMapping({"/", "/cryptoCurrencies"})
    public String currencyPage(Model model) {
//        model.addAttribute("currencies", cryptoCurrencyService.findAll());
//        model.addAttribute("currenciesPageActive", ACTIVE_PAGE_CLASS);
        return "currencyList";
    }

    @GetMapping("/cryptoExchanges")
    public String exchangePage(Model model) {
        model.addAttribute("exchanges", cryptoExchangeService.findAll());
        model.addAttribute("exchangesPageActive", ACTIVE_PAGE_CLASS);
        return "exchangeList";
    }

    @GetMapping("/cryptoCurrency/{id}")
    public String currencyInfoPage(Model model, @PathVariable Long id) {
        model.addAttribute("currency", cryptoCurrencyService.findById(id));
        return "currencyInfo";
    }

    @GetMapping("/news")
    public String newsPage(Model model) throws IOException {
        model.addAttribute("news", coinStatsGrabber.getAllNews());
        model.addAttribute("newsPageActive", ACTIVE_PAGE_CLASS);
        return "news";
    }

    @GetMapping("/statistics")
    public String statisticsPage(Model model) {
        model.addAttribute("statisticsPageActive", ACTIVE_PAGE_CLASS);
        return "statistics";
    }

    @GetMapping("/portfolio")
    public String portfolioPage(Model model) {
        model.addAttribute("portfolioPageActive", ACTIVE_PAGE_CLASS);
        return "portfolio";
    }
}
