package com.example.demo.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionRequest {

    @Schema(description = "藥局名稱", example = "First Care Rx")
    private String pharmacyName;
    @Schema(description = "口罩名稱", example = "True Barrier (green) (3 per pack)")
    private String maskName;
    @Schema(description = "使用者名稱", example = "Lester Arnold")
    private String userName;
    @Schema(description = "購買數量", example = "1")
    private BigDecimal num;
}
