package com.example.demo.bean;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import javax.swing.*;
import java.math.BigDecimal;

@Getter
@Setter
public class MaskVo {

    public MaskVo(){}

    MaskVo(JSONObject obj){
        this.name = obj.getString("name");
        this.price = obj.getBigDecimal("price");
    }

    private String name;

    private BigDecimal price;
}
