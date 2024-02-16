package com.example.oasismedicconnect.service;

import com.example.oasismedicconnect.configuration.jwt_configuration.JwtUtils;
import com.example.oasismedicconnect.model.Customer;
import com.example.oasismedicconnect.model.Supplier;
import com.example.oasismedicconnect.model.Token;
import com.example.oasismedicconnect.repositories.CustomerRepo;
import com.example.oasismedicconnect.repositories.SupplierRepo;
import com.example.oasismedicconnect.repositories.TokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class TokenService {
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private SupplierRepo supplierRepo;
    @Autowired
    private TokenRepo tokenRepo;
    public String generateToken(Object user) {
        String tokenResult;
        if (user instanceof Customer customer) {
            tokenResult = JwtUtils.generateToken(customer.getEmail());
        } else if (user instanceof Supplier supplier) {
            tokenResult = JwtUtils.generateToken(supplier.getEmail());
        } else {
            throw new IllegalArgumentException("Unsupported user type");
        }
        return tokenResult;
    }

    public boolean isTokenExpired(String token){
       return  JwtUtils.isTokenExpired(token);
    }

    public ResponseEntity<String> verifyTokenForCustomer(Long tokenId, String customerId, String tokenString) {
        Optional<Token> findCustomerToken = tokenRepo.findById(tokenId);

        // Guard clause: Check if token is present
        if (findCustomerToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token ID");
        }

        Token token = findCustomerToken.get();

        // Guard clause: Check if token is expired
        if (token.isExpired()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token has expired");
        }

        // Guard clause: Check if token is associated with a customer
        if (token.getCustomer() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token is not associated with a customer");
        }

        // Check if the provided customer ID and token string match
        if (!customerId.equals(token.getCustomer().getCustomerId()) || !tokenString.equals(token.getTokenString())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid customer ID or token string");
        }

        // Update the customer entity and return success response
        Customer customerEntity = token.getCustomer();
        customerEntity.setVerifiedUser(true);
        customerRepo.save(customerEntity);
        return ResponseEntity.status(HttpStatus.OK).body("Token is verified");
    }
    public ResponseEntity<List<Token>> getToken( ){
        return ResponseEntity.status(HttpStatus.OK).body(tokenRepo.findAll());
    }

    public boolean findCustomerTokenById(String id) {
        Optional<Token> findCustomer = tokenRepo.findTokenByCustomerCustomerId(id);
        return findCustomer.map(Token::getCustomer)
                .map(Customer::isVerifiedUser)
                .orElse(false);
    }


}
