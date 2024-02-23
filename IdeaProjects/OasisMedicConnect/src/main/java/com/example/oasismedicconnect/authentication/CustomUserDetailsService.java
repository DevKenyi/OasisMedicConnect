package com.example.oasismedicconnect.authentication;


import com.example.oasismedicconnect.enums.Roles;
import com.example.oasismedicconnect.model.Admin;
import com.example.oasismedicconnect.model.Customer;
import com.example.oasismedicconnect.model.Supplier;
import com.example.oasismedicconnect.repositories.AdminRepo;
import com.example.oasismedicconnect.repositories.CustomerRepo;
import com.example.oasismedicconnect.repositories.SupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private SupplierRepo supplierRepo;
    @Autowired
    private AdminRepo adminRepo;

    public CustomUserDetailsService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepo.findAdminByEmail(username);
        if (admin != null) {
            return User.builder()
                    .username(admin.getEmail())
                    .password(admin.getPassword())
                    .authorities(mapAuthorities(admin.getRoles()))
                    .credentialsExpired(false)
                    .accountLocked(false)
                    .disabled(false)
                    .accountExpired(false)
                    .build();

        }
        Optional<Customer> customer = customerRepo.findByEmail(username);
        if(customer.isPresent()){
            return User.builder()
                    .username(customer.get().getEmail())
                    .password(customer.get().getPassword())
                    .authorities(mapAuthorities(customer.get().getRoles()))
                    .credentialsExpired(false)
                    .accountLocked(false)
                    .disabled(false)
                    .accountExpired(false)
                    .build();
        }

        Optional<Supplier> supplier = supplierRepo.findByEmail(username);
        if(supplier.isPresent()){
            return User.builder()
                    .username(supplier.get().getEmail())
                    .password(supplier.get().getPassword())
                    .authorities(mapAuthorities(supplier.get().getRoles()))
                    .credentialsExpired(false)
                    .accountLocked(false)
                    .disabled(false)
                    .accountExpired(false)
                    .build();
        }
        throw new UsernameNotFoundException("User not found "+ HttpStatus.NOT_FOUND);
    }
    private Collection<? extends GrantedAuthority> mapAuthorities(Collection<Roles> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getRoles()))
                .collect(Collectors.toList());
    }
}
