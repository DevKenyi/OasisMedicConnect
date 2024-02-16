package com.example.oasismedicconnect.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID reviewId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customerId;
    private Double rating;
    private String comment;
    private LocalDateTime datePosted;


}
