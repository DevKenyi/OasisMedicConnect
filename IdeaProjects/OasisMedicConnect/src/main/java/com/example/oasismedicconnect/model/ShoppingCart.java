package com.example.oasismedicconnect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCart {
    @Id
    private Long cartId;
    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customerId;
}
