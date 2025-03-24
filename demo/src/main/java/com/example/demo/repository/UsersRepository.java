package com.example.demo.repository;


import com.example.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query(value = "SELECT USER_ID, NAME, CASH_BALANCE FROM USERS WHERE NAME = :queryName", nativeQuery = true)
    Users getUserByName(@Param("queryName") String queryName);
}
