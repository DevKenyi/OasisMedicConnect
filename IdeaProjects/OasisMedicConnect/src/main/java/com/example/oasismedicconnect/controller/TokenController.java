package com.example.oasismedicconnect.controller;

import com.example.oasismedicconnect.model.Token;
import com.example.oasismedicconnect.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Controller
@RequestMapping("api/")
public class TokenController {
    @Autowired
    private TokenService tokenService;
    @PutMapping("verify-customer-token/{tokenId}/{customerId}")

    public ResponseEntity<String> verifyCustomerToken(@PathVariable Long tokenId,
                                              @PathVariable String customerId,
                                              @RequestParam String tokenString) {

        return tokenService.verifyTokenForCustomer(tokenId, customerId, tokenString);
    }

    @GetMapping("token-data")
    public ResponseEntity<List<Token>> tokenList(){
        return tokenService.getToken();
    }





}
