package com.example.demo.service.impl;

import com.example.demo.bean.MaskVo;
import com.example.demo.bean.PharmacyVo;
import com.example.demo.entity.Mask;
import com.example.demo.repository.MaskRepository;
import com.example.demo.repository.OpeningHourRepository;
import com.example.demo.repository.PharmacyRepository;
import com.example.demo.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    private final OpeningHourRepository openingHourRepository;

    private final MaskRepository maskRepository;

    @Autowired
    public PharmacyServiceImpl(PharmacyRepository pharmacyRepository, OpeningHourRepository openingHourRepository, MaskRepository maskRepository) {
        this.pharmacyRepository = pharmacyRepository;
        this.openingHourRepository = openingHourRepository;
        this.maskRepository = maskRepository;
    }

    @Override
    public List<PharmacyVo> getPharmacyNameByTime(String time) {
        List<PharmacyVo> listPharmacyVo = new ArrayList<>();
        openingHourRepository.findPharmacyNameByTime(Time.valueOf(time)).forEach((ele) -> {
            PharmacyVo vo = new PharmacyVo();
            vo.setName(ele);
            listPharmacyVo.add(vo);
        });
        return listPharmacyVo;
    }

    @Override
    public List<PharmacyVo> getPharmacyNameByWeek(String week) {
        List<PharmacyVo> list = new ArrayList<>();
        openingHourRepository.findPharmacyNameByWeek(week).forEach((ele) -> {
            PharmacyVo vo = new PharmacyVo();
            vo.setName(ele);
            list.add(vo);
        });
        return list;
    }

    @Override
    public List<MaskVo> getMask(String name) {
        List<MaskVo> list = new ArrayList<>();
        maskRepository.getMaskByPharmacyName(name).forEach((ele)->{
            MaskVo vo = new MaskVo();
            vo.setName(ele.getName());
            vo.setPrice(ele.getPrice());
            list.add(vo);
        });
        return list;
    }

    @Override
    public Map<String, PharmacyVo> getMaskByPrice(String high, String low) {
        Map<String, PharmacyVo> map = new HashMap<>();
        maskRepository.getMaskByPrice(new BigDecimal(high), new BigDecimal(low)).forEach((ele)->{
            MaskVo maskVo = new MaskVo();
            maskVo.setName(ele.getName());
            maskVo.setPrice(ele.getPrice());
            String pharmacyName = ele.getPharmacy().getName();
            List<MaskVo> list = map.containsKey(pharmacyName)? map.get(pharmacyName).getMasks(): new ArrayList<MaskVo>();
            list.add(maskVo);
            PharmacyVo pharmacyVo = new PharmacyVo();
            pharmacyVo.setMasks(list);
            pharmacyVo.setName(ele.getPharmacy().getName());
            map.put(pharmacyName, pharmacyVo);
            System.out.println(ele.getPrice());
            System.out.println(pharmacyName);
        });
        return map;
    }
}
