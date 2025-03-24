package com.example.demo.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PharmacyRequest {

    @Schema(description = "藥局名稱", example = "First Care Rx")
    private String name;

    @Schema(description = "查詢藥局營業時間", example = "10:00:00 or Mon")
    private String time;

    @Schema(description = "查詢口罩價格低於此價格", example = "30")
    private String highPrice;
    @Schema(description = "查詢口罩價格高於此價格", example = "0")
    private String lowPrice;
}
