package com.example.oasismedicconnect.repositories;

import com.example.oasismedicconnect.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {

 Optional<Token> findTokenByCustomerCustomerId(String id);

 ;
}