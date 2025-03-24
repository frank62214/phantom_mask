package com.example.demo.repository;

import com.example.demo.bean.PharmacyVo;
import com.example.demo.entity.OpeningHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface OpeningHourRepository extends JpaRepository<OpeningHour, Long> {



    @Query(value = "SELECT PH.NAME FROM PUBLIC.OPENING_HOUR OH LEFT JOIN PUBLIC.PHARMACY PH ON OH.PHARMACY_ID = PH.PHARMACY_ID WHERE (start_time <= :queryTime AND end_time >= :queryTime) GROUP BY PH.NAME", nativeQuery = true)
    List<String> findPharmacyNameByTime(@Param("queryTime") Time queryTime);


    @Query(value = "SELECT PH.NAME FROM PUBLIC.OPENING_HOUR OH LEFT JOIN PUBLIC.PHARMACY PH ON OH.PHARMACY_ID = PH.PHARMACY_ID WHERE WEEK = :queryWeek", nativeQuery = true)
    List<String> findPharmacyNameByWeek(@Param("queryWeek") String queryWeek);
}
