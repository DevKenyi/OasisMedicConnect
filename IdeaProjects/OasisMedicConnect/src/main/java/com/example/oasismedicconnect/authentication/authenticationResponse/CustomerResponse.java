package com.example.oasismedicconnect.authentication.authenticationResponse;

import com.example.oasismedicconnect.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private String jwtToken;
    private String userRole;
    private String userName;
    private String firstname;
    private String lastname;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private String Id;
    private String address;

}
