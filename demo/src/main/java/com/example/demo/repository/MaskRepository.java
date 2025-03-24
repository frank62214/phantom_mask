package com.example.demo.repository;

import com.example.demo.entity.Mask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MaskRepository extends JpaRepository<Mask, Long> {

    @Query(value = "SELECT MA.MASK_ID, MA.PHARMACY_ID, MA.NAME, MA.PRICE FROM PUBLIC.MASK MA LEFT JOIN PUBLIC.PHARMACY PH ON MA.PHARMACY_ID = PH.PHARMACY_ID where PH.NAME = :queryName ORDER BY MA.PRICE", nativeQuery = true)
    List<Mask> getMaskByPharmacyName(@Param("queryName") String queryName);

    @Query(value = "SELECT MA.MASK_ID, MA.PHARMACY_ID, MA.NAME, MA.PRICE FROM PUBLIC.MASK MA LEFT JOIN PUBLIC.PHARMACY PH ON MA.PHARMACY_ID = PH.PHARMACY_ID where PH.NAME = :pharmacyName AND MA.NAME = :maskName LIMIT 1", nativeQuery = true)
    Mask getMaskByPharmacyNameAndMaskName(@Param("pharmacyName") String pharmacyName, @Param("maskName") String maskName);

    @Query(value = "SELECT MA.MASK_ID, MA.PHARMACY_ID, MA.NAME, MA.PRICE FROM PUBLIC.MASK MA LEFT JOIN PUBLIC.PHARMACY PH ON MA.PHARMACY_ID = PH.PHARMACY_ID WHERE MA.PRICE < :highPrice AND MA.PRICE > :lowPrice", nativeQuery = true)
    List<Mask> getMaskByPrice(@Param("highPrice") BigDecimal highPrice, @Param("lowPrice") BigDecimal lowPrice);

    @Query(value = "SELECT NAME FROM MASK WHERE to_tsvector('english', name) @@ to_tsquery('english', :queryName) GROUP BY NAME", nativeQuery = true)
    List<String> search(@Param("queryName") String queryName);
}
