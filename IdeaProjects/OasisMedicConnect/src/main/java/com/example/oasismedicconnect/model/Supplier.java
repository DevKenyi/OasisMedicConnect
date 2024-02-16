package com.example.oasismedicconnect.model;

import com.example.oasismedicconnect.enums.Gender;
import com.example.oasismedicconnect.enums.Roles;
import com.example.oasismedicconnect.model.utils.IdGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Collection;
import java.util.Set;
@Data
@Entity
public class Supplier extends User {
    @Id
    private String supplierId;
    @OneToMany(mappedBy = "supplier")
    private Set<Product> products;

    public Supplier(String firstname, String lastname, String email, String phoneNumber, String alternatePhoneNumber, String password, String confirmPassword, Collection<Roles> roles, String address, boolean verifiedUser, Gender gender) {
        super(firstname, lastname, email, phoneNumber, alternatePhoneNumber, password, confirmPassword, roles, address, verifiedUser, gender);
    }

    public Supplier() {
        super(null, null, null, null, null, null, null, null, null, false, null);
        this.supplierId = IdGenerator.generateRandomId();
    }
}
