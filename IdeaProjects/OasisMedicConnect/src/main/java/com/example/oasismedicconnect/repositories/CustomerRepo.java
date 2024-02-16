package com.example.oasismedicconnect.repositories;

import com.example.oasismedicconnect.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, String> {
    Optional<Customer> findByEmail(String email);


}