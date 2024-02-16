package com.example.oasismedicconnect.model;

import com.example.oasismedicconnect.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    private UUID productId;
    private String name;
    private String Description;
    private Collection<Category> categories;
    private BigDecimal price;
    private int stockQuantity;
    @ManyToOne
    private Supplier supplier;

    @OneToMany(mappedBy = "product")
    private Set<OrderItem> orderItems;


}
