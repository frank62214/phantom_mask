package com.example.demo.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchBean {

    List<String> searchMaskList = new ArrayList<>();

    List<String> searchPharmacyList = new ArrayList<>();
}
