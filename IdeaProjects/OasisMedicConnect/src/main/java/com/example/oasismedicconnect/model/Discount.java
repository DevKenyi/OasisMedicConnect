package com.example.oasismedicconnect.model;

import java.time.LocalDateTime;

public class Discount {
    //todo to be used in future release
    private  Long discountId;
    private Product productId;
    private double discountPercentage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
