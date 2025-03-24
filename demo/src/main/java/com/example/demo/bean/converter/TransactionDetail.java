package com.example.demo.bean.converter;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionDetail {
     private String transactionStatus;
     private String pharmacyName;
     private String maskName;
     private String userName;
     private BigDecimal transactionAmount;
}
