package com.example.oasismedicconnect.model;

import com.example.oasismedicconnect.enums.Gender;
import com.example.oasismedicconnect.enums.Roles;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Collection;
import java.util.UUID;

@Entity
public class Admin extends User {
    @Id
    private UUID adminId;


    public Admin(String firstname, String lastname, String email, String phoneNumber, String alternatePhoneNumber, String password, String confirmPassword, Collection<Roles> roles, String address, boolean verifiedUser, Gender gender) {
        super(firstname, lastname, email, phoneNumber, alternatePhoneNumber, password, confirmPassword, roles, address, verifiedUser, gender);
    }

    public Admin() {
        super(null, null, null, null, null, null, null, null, null, false, null);
    }
}
