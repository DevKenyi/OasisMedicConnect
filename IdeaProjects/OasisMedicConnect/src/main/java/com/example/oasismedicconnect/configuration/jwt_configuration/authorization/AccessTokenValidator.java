package com.example.oasismedicconnect.configuration.jwt_configuration.authorization;

import com.example.oasismedicconnect.model.Customer;
import com.example.oasismedicconnect.repositories.CustomerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class AccessTokenValidator {
    @Autowired
    private TokenAuthorization tokenAuthorization;
    @Autowired
    private CustomerRepo customerRepo;

    public String getAuthorizationHeader(String authorizationHeader){
        return tokenAuthorization.authorizeToken(authorizationHeader);
    }
    public ResponseEntity<Customer> verifyCustomerTokenByEmail(String authHeader){
        String extractEmailFromToken = getAuthorizationHeader(authHeader);
        Optional<Customer> findCustomerByEmail = customerRepo.findByEmail(extractEmailFromToken);
        if(findCustomerByEmail.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
