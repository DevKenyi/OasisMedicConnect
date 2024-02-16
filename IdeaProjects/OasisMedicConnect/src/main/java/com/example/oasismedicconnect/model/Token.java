package com.example.oasismedicconnect.model;

import com.example.oasismedicconnect.service.RegistrationService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "token")
    private String tokenString;

    @Column(name = "token_issued_at")
    private Date issuedAt;

    @Column(name = "token_expiration")
    private Date expiration;

    @Column(name = "expired")

    private boolean isExpired;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;





}
