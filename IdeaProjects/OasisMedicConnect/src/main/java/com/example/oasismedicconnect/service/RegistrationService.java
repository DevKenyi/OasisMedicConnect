package com.example.oasismedicconnect.service;

import com.example.oasismedicconnect.configuration.jwt_configuration.JwtUtils;
import com.example.oasismedicconnect.emai_service.EmailService;
import com.example.oasismedicconnect.enums.Roles;
import com.example.oasismedicconnect.model.Customer;
import com.example.oasismedicconnect.model.Supplier;
import com.example.oasismedicconnect.model.Token;
import com.example.oasismedicconnect.repositories.CustomerRepo;
import com.example.oasismedicconnect.repositories.SupplierRepo;
import com.example.oasismedicconnect.repositories.TokenRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
public class RegistrationService {
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SupplierRepo supplierRepo;

    @Autowired
    private TokenRepo tokenRepo;

    @Autowired
    private TokenService tokenService;
    Customer customerEntity = new Customer();
    Supplier supplierEntity = new Supplier();

    @Autowired
    private EmailService emailService;




    public ResponseEntity<String> customerOnboarding(Customer customer) {
        ResponseEntity<String> validatePasswordResponse = customer.validatePassword();
        if (!validatePasswordResponse.getStatusCode().is2xxSuccessful()) {
            // Password validation failed
            return validatePasswordResponse;
        }

        Optional<Customer> existingCustomer = customerRepo.findByEmail(customer.getEmail());
        if (existingCustomer.isPresent()) {
            // Customer with the same email already exists
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with email " + customer.getEmail() + " already exists");
        }

        // Encode the password before saving the customer entity
        customer.encodePassword(passwordEncoder);

        // Set other attributes and save the customer entity
        Customer customerEntity = new Customer();
        customerEntity.setFirstname(customer.getFirstname());
        customerEntity.setLastname(customer.getLastname());
        customerEntity.setEmail(customer.getEmail());
        customerEntity.setPhoneNumber(customer.getPhoneNumber());
        customerEntity.setAddress(customer.getAddress());
        customerEntity.setAlternatePhoneNumber(customer.getAlternatePhoneNumber());
        customerEntity.setRoles(Collections.singleton(Roles.Customer));
        customerEntity.setGender(customer.getGender());
        customerEntity.setPassword(customer.getPassword());

        try {
            // Save the customer entity
            customerRepo.save(customerEntity);

            // Generate and save token for the customer
            Token token = new Token();
            token.setCustomer(customerEntity); // Set the customer for the token
            String generatedToken = tokenService.generateToken(customerEntity);
            token.setTokenString(generatedToken); // Set the generated token
            token.setIssuedAt(JwtUtils.extractIssuedAt(generatedToken));
            token.setExpiration(JwtUtils.extractExpiration(generatedToken));
            token.setExpired(tokenService.isTokenExpired(generatedToken));

            tokenRepo.save(token);
            //send link to users email
            emailService.sendTokenEmail(generatedToken, customerEntity.getEmail());

            log.info("Token generated and saved for customer: " + customerEntity.getEmail());


            return ResponseEntity.status(HttpStatus.CREATED).body("Click the link sent to your email address to verify your token " + generatedToken);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during customer registration");
        }
    }



    public ResponseEntity<String> supplierOnboarding(Supplier supplier) {
        ResponseEntity<String> validatePasswordResponse = supplier.validatePassword();

        if (!validatePasswordResponse.getStatusCode().is2xxSuccessful()) {
            // Password validation failed
            return validatePasswordResponse;
        }

        Optional<Supplier> existingSupplier = supplierRepo.findByEmail(supplier.getEmail());
        if (existingSupplier.isPresent()) {
            // Supplier with the same email already exists
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Supplier with email " + supplier.getEmail() + " already exists");
        }

        // Encode the password before saving the supplier entity
        supplier.encodePassword(passwordEncoder);
        supplierEntity.setFirstname(supplier.getFirstname());
        supplierEntity.setLastname(supplier.getLastname());
        supplierEntity.setEmail(supplier.getEmail());
        supplierEntity.setPhoneNumber(supplier.getPhoneNumber());
        supplierEntity.setAddress(supplier.getAddress());
        supplierEntity.setAlternatePhoneNumber(supplier.getAlternatePhoneNumber());
        supplierEntity.setRoles(Collections.singleton(Roles.Supplier));
        supplierEntity.setGender(supplier.getGender());
        supplierEntity.setPassword(supplier.getPassword());

        // Save the supplier entity
        supplierRepo.save(supplierEntity);

        try {
            // Save the customer entity
            supplierRepo.save(supplierEntity);

            // Generate and save token for the customer
            Token token = new Token();
           token.setSupplier(supplierEntity); // Set the customer for the token
            return getStringResponseEntity(token, tokenService.generateToken(supplierEntity), supplierEntity.getEmail(), supplierEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during customer registration");
        }


    }

    private ResponseEntity<String> getStringResponseEntity(Token token, String s, String email, Object o) {
        token.setTokenString(s); // Generate token for the customer
        token.setIssuedAt(JwtUtils.extractIssuedAt(token.getTokenString()));
        token.setExpiration(JwtUtils.extractExpiration(token.getTokenString()));
        //Todo upon expiration of token user should be click to resend token again
        token.setExpired(tokenService.isTokenExpired(token.getTokenString()));
        tokenRepo.save(token);
        log.info("Token generated and saved for customer: " + email);

        return ResponseEntity.status(HttpStatus.CREATED).body("Click the link sent to your email address to verify your token "+ token.getTokenString());
    }








}