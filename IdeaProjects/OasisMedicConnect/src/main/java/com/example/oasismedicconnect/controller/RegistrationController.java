package com.example.oasismedicconnect.controller;

import com.example.oasismedicconnect.model.Customer;
import com.example.oasismedicconnect.model.Supplier;
import com.example.oasismedicconnect.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @PostMapping("register/customer")
    public ResponseEntity<String>registerCustomer(@RequestBody Customer customer){
        return registrationService.customerOnboarding(customer);
    }

    @PostMapping("register/supplier")
    public ResponseEntity<String> registerSupplier(@RequestBody Supplier supplier){
        return  registrationService.supplierOnboarding(supplier);
    }




}
