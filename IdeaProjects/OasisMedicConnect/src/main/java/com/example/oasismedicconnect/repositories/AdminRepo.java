package com.example.oasismedicconnect.repositories;

import com.example.oasismedicconnect.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin, String> {
    Admin findAdminByEmail(String username);
}
