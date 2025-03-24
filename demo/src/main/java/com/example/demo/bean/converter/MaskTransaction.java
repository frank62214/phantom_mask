package com.example.demo.bean.converter;

import java.math.BigDecimal;

public interface MaskTransaction {
    BigDecimal getTransactionAmount();
    BigDecimal getMaskCount();
    String getMaskName();
}
