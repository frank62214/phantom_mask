package com.example.demo.repository;


import com.example.demo.bean.converter.MaskTransaction;
import com.example.demo.bean.converter.TopTransactionAmount;
import com.example.demo.entity.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {

    @Query(value = "SELECT  SUM AS transactionAmount, US.NAME AS pharmacyName" +
            "  FROM (SELECT SUM(TRANSACTION_AMOUNT) AS SUM" +
            "     , USER_ID FROM PURCHASE_HISTORY" +
            " WHERE TRANSACTION_DATE BETWEEN :startTime AND :endTime" +
            " GROUP BY USER_ID) PU LEFT JOIN USERS US ON PU.USER_ID = US.USER_ID" +
            " ORDER BY SUM DESC" +
            " LIMIT :row", nativeQuery = true)
    List<TopTransactionAmount> findHighTransactionAmountUser(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("row") int row);

    @Query(value = "SELECT MASK_NAME AS maskName, SUM(TRANSACTION_AMOUNT) AS transactionAmount, COUNT(MASK_NAME) AS maskCount FROM PURCHASE_HISTORY" +
            " WHERE TRANSACTION_DATE BETWEEN :startTime AND :endTime" +
            " GROUP BY MASK_NAME", nativeQuery = true)
    List<MaskTransaction> queryPurchaseMaskByDate(@Param("startTime") Date startTime, @Param("endTime")Date endTime);
}
