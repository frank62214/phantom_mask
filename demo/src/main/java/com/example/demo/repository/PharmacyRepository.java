package com.example.demo.repository;

import com.example.demo.bean.PharmacyVo;
import com.example.demo.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

    @Query(value = "SELECT PHARMACY_ID, NAME, CASH_BALANCE FROM PHARMACY WHERE NAME = :queryName", nativeQuery = true)
    Pharmacy getPharmacyByName(@Param("queryName") String queryName);

    @Query(value = "SELECT NAME FROM PHARMACY WHERE to_tsvector('english', name) @@ to_tsquery('english', :queryName)  GROUP BY NAME", nativeQuery = true)
    List<String> search(@Param("queryName") String queryName);

}
