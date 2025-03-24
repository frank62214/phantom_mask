package com.example.demo.bean;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;


@Getter
@Setter
public class OpeningHourVo {

    private String week;

    private Time startTime;

    private Time endTime;
}
