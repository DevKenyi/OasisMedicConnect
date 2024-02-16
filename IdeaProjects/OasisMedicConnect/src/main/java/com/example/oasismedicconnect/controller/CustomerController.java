package com.example.oasismedicconnect.controller;

import com.example.oasismedicconnect.model.Customer;
import com.example.oasismedicconnect.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    private CustomerRepo customerRepo;
    @GetMapping("/customer-data")
    public ResponseEntity<List<Customer> >findCustomer(){
        return ResponseEntity.status(HttpStatus.OK).body( customerRepo.findAll());
    }
}
