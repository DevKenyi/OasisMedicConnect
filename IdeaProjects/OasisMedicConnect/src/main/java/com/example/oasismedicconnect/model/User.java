package com.example.oasismedicconnect.model;

import com.example.oasismedicconnect.enums.Gender;
import com.example.oasismedicconnect.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j


public abstract class User {

    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String alternatePhoneNumber;
    private String password;
    private String confirmPassword;
    @Column(name = "user_roles")
    @Enumerated(EnumType.STRING)
    private Collection<Roles> roles;
    @NotNull

    private String address;
    private boolean verifiedUser = false;
    private Gender gender;


   @Bean
    public ResponseEntity<String> validatePassword() {
        if (!password.equals(confirmPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password does not match");
        }
        if (password.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Kindly input a password please");
        }
        log.info("");
       return ResponseEntity.status(HttpStatus.OK).body("Password is validated successfully");

   }
    @Bean
    public void encodePassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }

}
