package com.example.oasismedicconnect.model;

import com.example.oasismedicconnect.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    private UUID orderId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private Collection<Status> status;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customerId;

}
