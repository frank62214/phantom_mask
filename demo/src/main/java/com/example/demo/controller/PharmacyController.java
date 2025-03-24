package com.example.demo.controller;

import com.example.demo.bean.MaskVo;
import com.example.demo.bean.PharmacyRequest;
import com.example.demo.bean.PharmacyVo;
import com.example.demo.service.PharmacyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name="藥局口罩資訊相關API")
@RestController
@CrossOrigin(origins = "*")
public class PharmacyController {
    private final PharmacyService pharmacyService;

    @Autowired
    public PharmacyController(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @PostMapping("/queryOpeningHour")
    @Operation(summary = "取得藥局的開門時間")
    public List<PharmacyVo> queryOpeningHours(@RequestBody PharmacyRequest pharmacyRequest) {
        System.out.println(pharmacyRequest.getTime());
        if(pharmacyRequest.getTime().contains(":")){
            // 判斷是時間區間
            return pharmacyService.getPharmacyNameByTime(pharmacyRequest.getTime());
        }
        return pharmacyService.getPharmacyNameByWeek(pharmacyRequest.getTime());
    }

    @PostMapping("/queryMask")
    @Operation(summary = "取得藥局所販賣的口罩")
    public List<MaskVo> queryMask(@RequestBody PharmacyRequest pharmacyRequest) {
        System.out.println(pharmacyRequest.getName());
        return pharmacyService.getMask(pharmacyRequest.getName());
    }

    @PostMapping("/queryMaskByPrice")
    @Operation(summary = "取得價格區間的口罩")
    public Map<String, PharmacyVo> queryMaskByPrice(@RequestBody PharmacyRequest pharmacyRequest) {
        return pharmacyService.getMaskByPrice(pharmacyRequest.getHighPrice(), pharmacyRequest.getLowPrice());
    }
}
