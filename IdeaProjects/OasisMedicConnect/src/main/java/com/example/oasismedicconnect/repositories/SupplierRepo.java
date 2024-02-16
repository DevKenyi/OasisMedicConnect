package com.example.oasismedicconnect.repositories;

import com.example.oasismedicconnect.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupplierRepo extends JpaRepository<Supplier, String> {
    Optional<Supplier> findByEmail(String email);
}
