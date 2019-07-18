package com.crypto.controller;

import com.crypto.service.CryptoCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    @GetMapping({"/", "/cryptocurrencies"})
    public String mainPage(Model model) {
        model.addAttribute("currencies", cryptoCurrencyService.getAllCryptoCurrencies());
        return "main";
    }
}
