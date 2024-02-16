package com.example.oasismedicconnect.service;

import com.example.oasismedicconnect.configuration.jwt_configuration.authorization.AccessTokenValidator;
import com.example.oasismedicconnect.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SupplierService {
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccessTokenValidator accessTokenValidator;

}
