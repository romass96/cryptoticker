package com.crypto.controller;

import com.crypto.service.CryptoCurrencyService;
import com.crypto.service.CryptoExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    @Autowired
    private CryptoExchangeService cryptoExchangeService;

    @GetMapping({"/", "/cryptocurrencies"})
    public String currencyPage(Model model) {
        model.addAttribute("currencies", cryptoCurrencyService.getAllCryptoCurrencies());
        return "main";
    }

    @GetMapping("/cryptoexchanges")
    public String exchangePage(Model model) {
        model.addAttribute("exchanges", cryptoExchangeService.findAll());
        return "exchangeList";
    }
}
