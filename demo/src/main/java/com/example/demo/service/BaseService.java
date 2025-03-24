package com.example.demo.service;

import com.example.demo.bean.*;
import com.example.demo.bean.converter.MaskTransaction;
import com.example.demo.bean.converter.TopTransactionAmount;
import com.example.demo.bean.converter.TransactionDetail;

import java.util.List;
import java.util.Objects;

public interface BaseService {

    RestBean<Objects> initData();
    
    TransactionDetail transaction(TransactionRequest transactionRequest);

    List<TopTransactionAmount> findHighTransactionAmountUser(QueryRequest queryRequest);

    RestBean<List<MaskTransaction>> queryPurchaseMaskByDate(QueryRequest queryRequest);

    List<String> searchMask(String item);

    List<String> searchPharmacy(String item);
}
