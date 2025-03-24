package com.example.demo.bean;

import com.example.demo.util.Util;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

@Getter
@Setter
public class PurchaseHistoryVo {

    public PurchaseHistoryVo(){}

    public PurchaseHistoryVo(JSONObject object){
        this.pharmacyName = object.getString("pharmacyName");
        this.maskName = object.getString("maskName");
        this.transactionAmount = object.getBigDecimal("transactionAmount");

        String dateString = object.getString("transactionDate");
        try {
            this.transactionDate = Util.transferDate(dateString);
        }
        catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }



    private String pharmacyName;

    private String maskName;

    private BigDecimal transactionAmount;

    private Date transactionDate;
}
