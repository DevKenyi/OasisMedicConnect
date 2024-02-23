package com.example.oasismedicconnect.authentication.authenticaton_controller;

import com.example.oasismedicconnect.configuration.jwt_configuration.JwtUtils;
import com.example.oasismedicconnect.authentication.authenticationResponse.CustomerResponse;
import com.example.oasismedicconnect.authentication.authenticationResponse.SupplierResponse;
import com.example.oasismedicconnect.model.Customer;
import com.example.oasismedicconnect.model.LoginRequest;
import com.example.oasismedicconnect.model.Supplier;
import com.example.oasismedicconnect.repositories.CustomerRepo;
import com.example.oasismedicconnect.repositories.SupplierRepo;
import com.example.oasismedicconnect.repositories.TokenRepo;
import com.example.oasismedicconnect.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("api/")
public class AuthenticateUserController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private SupplierRepo supplierRepo;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TokenRepo tokenRepo;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest,
                                              HttpServletRequest request,
                                              HttpServletResponse response) {
        log.info("Login request here " + loginRequest);

        try {
            // Authenticate the user using Spring Security's authenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            // Set the authentication in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = JwtUtils.generateToken(userDetails);

            // Get user role and create appropriate response
            String userRole = userDetails.getAuthorities().iterator().next().getAuthority();
            switch (userRole) {
                case "ROLE_Customer": // Update the role comparison


                    Optional<Customer> customer = customerRepo.findByEmail(loginRequest.getEmail());


                    if (customer.isPresent()) {
                        if(tokenService.findCustomerTokenById(customer.get().getCustomerId())) {
                            CustomerResponse customerResponse = new CustomerResponse();
                            customerResponse.setJwtToken(jwtToken);
                            customerResponse.setId(customer.get().getCustomerId());
                            customerResponse.setUserRole(userRole);

                            customerResponse.setPhoneNumber(customer.get().getPhoneNumber());
                            customerResponse.setEmail(customer.get().getEmail());
                            customerResponse.setAddress(customer.get().getAddress());
                            customerResponse.setFirstname(customer.get().getFirstname());
                            customerResponse.setLastname(customer.get().getLastname());
                            return ResponseEntity.ok().body(customerResponse);
                        }
                        else
                            return  ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("You need to verify your " +
                                    "account to be able to log in");
                    }
                    break;
                case "ROLE_Supplier":
                    Optional<Supplier> supplier = supplierRepo.findByEmail(loginRequest.getEmail());
                    if (supplier.isPresent()) {
                        SupplierResponse supplierResponse = new SupplierResponse();
                        supplierResponse.setJwtToken(jwtToken);
                        supplierResponse.setId(supplier.get().getSupplierId());
                        supplierResponse.setUserRole(userRole);
                        supplierResponse.setPhoneNumber(supplier.get().getPhoneNumber());
                        supplierResponse.setEmail(supplier.get().getEmail());
                        supplierResponse.setAddress(supplier.get().getAddress());
                        supplierResponse.setFirstname(supplier.get().getFirstname());
                        supplierResponse.setLastname(supplier.get().getLastname());
                        return ResponseEntity.ok().body(supplierResponse);
                    }
                    break;
            }
        } catch (AuthenticationException e) {
            // Handle authentication exception
            log.error("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}



