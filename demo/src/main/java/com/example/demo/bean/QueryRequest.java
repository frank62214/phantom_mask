package com.example.demo.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryRequest {
    @Schema(description = "開始時間", example = "2021-01-20")
    private String startTime;
    @Schema(description = "結束時間", example = "2021-01-21")
    private String endTime;
    @Schema(description = "行數", example = "2")
    private int row;
}
