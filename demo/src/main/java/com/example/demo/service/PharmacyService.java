package com.example.demo.service;

import com.example.demo.bean.MaskVo;
import com.example.demo.bean.PharmacyVo;

import java.util.List;
import java.util.Map;

public interface PharmacyService{

    List<PharmacyVo> getPharmacyNameByTime(String time);

    List<PharmacyVo> getPharmacyNameByWeek(String week);

    List<MaskVo> getMask(String name);

    Map<String, PharmacyVo> getMaskByPrice(String high, String low);
}
