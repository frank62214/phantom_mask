package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Entity
@Getter
@Setter
public class OpeningHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long OpeningHourId;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id", nullable = false)
    private Pharmacy pharmacy;

    @Column(nullable = false, length = 100)
    private String week;

    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private Time startTime;

    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private Time endTime;
}
