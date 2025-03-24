package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Mask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maskId;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id", nullable = false)
    private Pharmacy pharmacy;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;
}
