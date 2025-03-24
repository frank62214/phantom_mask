package com.example.demo.bean;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserVo {

    public UserVo(){}

    public UserVo(JSONObject object){
        this.name = object.getString("name");
        this.cashBalance = object.getBigDecimal("cashBalance");
        JSONArray purchaseHistories = object.getJSONArray("purchaseHistories");
        purchaseHistories.forEach((e) -> {
            JSONObject obj = new JSONObject(e.toString());
            PurchaseHistoryVo purchaseHistoryVo = new PurchaseHistoryVo(obj);
            this.purchaseHistories.add(purchaseHistoryVo);
        });
    }
    private String name;

    private BigDecimal cashBalance;

    private List<PurchaseHistoryVo> purchaseHistories = new ArrayList<PurchaseHistoryVo>();
}
