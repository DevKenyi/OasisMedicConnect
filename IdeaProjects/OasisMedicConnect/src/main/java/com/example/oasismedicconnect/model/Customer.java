package com.example.oasismedicconnect.model;

import com.example.oasismedicconnect.enums.Gender;
import com.example.oasismedicconnect.enums.Roles;
import com.example.oasismedicconnect.model.utils.IdGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
public class Customer extends User {
    @Id
    private String customerId;

    public Customer(String firstname, String lastname, String email, String phoneNumber, String alternatePhoneNumber, String password, String confirmPassword, Collection<Roles> roles, String address, boolean verifiedUser, Gender gender) {
        super(firstname, lastname, email, phoneNumber, alternatePhoneNumber, password, confirmPassword, roles, address, verifiedUser, gender);

    }


    public Customer() {
        super(null, null, null, null, null, null, null, null, null, false, null);
        this.customerId = IdGenerator.generateRandomId();

    }
}
