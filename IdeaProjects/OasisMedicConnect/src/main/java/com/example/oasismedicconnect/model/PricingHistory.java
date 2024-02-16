package com.example.oasismedicconnect.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PricingHistory {
    private UUID pricingHistoryId;

    private BigDecimal price;
    private LocalDateTime effectiveDate;
    private String description;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
