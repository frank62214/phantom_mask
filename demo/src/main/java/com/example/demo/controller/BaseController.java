package com.example.demo.controller;

import com.example.demo.bean.QueryRequest;
import com.example.demo.bean.RestBean;
import com.example.demo.bean.SearchBean;
import com.example.demo.bean.TransactionRequest;
import com.example.demo.bean.converter.MaskTransaction;
import com.example.demo.bean.converter.TopTransactionAmount;
import com.example.demo.bean.converter.TransactionDetail;
import com.example.demo.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Tag(name="交易相關API")
@RestController
@CrossOrigin(origins = "*")
public class BaseController {

    private final BaseService baseService;

    public BaseController(BaseService baseService) {
        this.baseService = baseService;
    }

    @GetMapping("/initData")
    @Operation(summary = "初始化資料表")
    public RestBean<Objects> initData(){
        return baseService.initData();
    }

    @PostMapping("transaction")
    @Operation(summary = "基本交易功能")
    public RestBean<TransactionDetail> transaction(@RequestBody TransactionRequest transactionRequest){
        RestBean<TransactionDetail> response = new RestBean<TransactionDetail>();
        TransactionDetail detail =  baseService.transaction(transactionRequest);
        if(!detail.getTransactionAmount().equals(BigDecimal.ZERO)){
            response.setBody(detail);
            response.setStatus("Transaction Success");
        }
        else{
            response.setStatus("Transaction Failed");
        }
        return response;
    }

    @PostMapping("queryHighTransactionAmountUser")
    @Operation(summary = "取得時間區間內，相對應數量的使用者，由金額高到低排序")
    public RestBean<List<TopTransactionAmount>> queryHighTransactionAmountUser(@RequestBody QueryRequest query){
        System.out.println(query.getRow());
        System.out.println(query.getStartTime());
        System.out.println(query.getEndTime());
        RestBean<List<TopTransactionAmount>> response = new RestBean<>();
        response.setBody(baseService.findHighTransactionAmountUser(query));
        response.setStatus("請求成功");
        if(response.getBody() == null){
            response.setStatus("輸入錯誤");
        }
        return response;
    }

    @PostMapping("queryPurchaseMaskByDate")
    @Operation(summary = "取得時間區間內口罩的交易數量")
    public RestBean<List<MaskTransaction>> queryPurchaseMaskByDate(@RequestBody QueryRequest query){
        System.out.println(query.getStartTime());
        System.out.println(query.getEndTime());
        return baseService.queryPurchaseMaskByDate(query);
    }

    @GetMapping("search")
    @Operation(summary = "搜尋功能")
    public RestBean<SearchBean> search(@PathVariable String type){
        RestBean<SearchBean> response = new RestBean<>();
        SearchBean search = new SearchBean();
        search.setSearchMaskList(baseService.searchMask(type));
        search.setSearchPharmacyList(baseService.searchPharmacy(type));
        response.setBody(search);
        return response;
    }
}
