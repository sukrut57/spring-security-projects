package com.spring.security.eazybackendapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @GetMapping("/account-details")
    public String getAccountDetails() {
        return "Get Account Details";
    }

}
