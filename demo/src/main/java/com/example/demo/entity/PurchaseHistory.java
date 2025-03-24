package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
public class PurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseHistoryId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id", nullable = false)
    private Pharmacy pharmacy;

    @Column(nullable = false)
    private  String maskName;

    @Column(nullable = false)
    private BigDecimal transactionAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date transactionDate;
}
