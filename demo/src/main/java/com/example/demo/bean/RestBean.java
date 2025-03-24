package com.example.demo.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestBean<T> {

    private String status = "";

    private T body;
}
