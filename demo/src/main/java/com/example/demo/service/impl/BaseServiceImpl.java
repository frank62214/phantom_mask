package com.example.demo.service.impl;

import com.example.demo.bean.*;
import com.example.demo.bean.converter.MaskTransaction;
import com.example.demo.bean.converter.TopTransactionAmount;
import com.example.demo.bean.converter.TransactionDetail;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.BaseService;
import com.example.demo.util.Util;
import jakarta.transaction.Transactional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class BaseServiceImpl implements BaseService {

    private final PharmacyRepository pharmacyRepository;

    private final MaskRepository maskRepository;

    private final OpeningHourRepository openingHourRepository;

    private final UsersRepository usersRepository;

    private final PurchaseHistoryRepository purchaseHistoryRepository;

    @Autowired
    public BaseServiceImpl(PharmacyRepository pharmacyRepository, MaskRepository maskRepository, OpeningHourRepository openingHourRepository, UsersRepository usersRepository, PurchaseHistoryRepository purchaseHistoryRepository) {
        this.pharmacyRepository = pharmacyRepository;
        this.maskRepository = maskRepository;
        this.openingHourRepository = openingHourRepository;
        this.usersRepository = usersRepository;
        this.purchaseHistoryRepository = purchaseHistoryRepository;
    }

    @Override
    public RestBean<Objects> initData() {
        RestBean<Objects> result = new RestBean<>();
        try{
            purchaseHistoryRepository.deleteAll();
            usersRepository.deleteAll();
            maskRepository.deleteAll();
            openingHourRepository.deleteAll();
            pharmacyRepository.deleteAll();
            File pharmaciesFile = ResourceUtils.getFile("classpath:initData/pharmacies.json");
            JSONArray pharmacies = new JSONArray(new String(Files.readAllBytes(pharmaciesFile.toPath())));
            pharmacies.forEach((e) -> {
                JSONObject obj = new JSONObject(e.toString());
                PharmacyVo pharmacyVo = new PharmacyVo(obj);
                Pharmacy pharmacy = new Pharmacy();
                pharmacy.setName(pharmacyVo.getName());
                pharmacy.setCashBalance(pharmacyVo.getCashBalance());

                // 儲存藥局 取得ID
                pharmacyRepository.save(pharmacy);
                // 儲存該藥局的口罩
                pharmacyVo.getMasks().forEach((ele) -> {
                    Mask mask = new Mask();
                    mask.setPharmacy(pharmacy);
                    mask.setName(ele.getName());
                    mask.setPrice(ele.getPrice());
                    maskRepository.save(mask);
                });
                // 儲存營業時間
                pharmacyVo.getOpeningHours().forEach((week) -> {
                    OpeningHour openingHour = new OpeningHour();
                    openingHour.setPharmacy(pharmacy);
                    openingHour.setWeek(week.getWeek());
                    openingHour.setStartTime(week.getStartTime());
                    openingHour.setEndTime(week.getEndTime());
                    openingHourRepository.save(openingHour);
                });
            });
            File usersFile = ResourceUtils.getFile("classpath:initData/users.json");
            JSONArray users = new JSONArray(new String(Files.readAllBytes(usersFile.toPath())));
            users.forEach((e)-> {

                JSONObject obj = new JSONObject(e.toString());
                UserVo userVo = new UserVo(obj);
                Users user = new Users();
                user.setName(userVo.getName());
                user.setCashBalance(userVo.getCashBalance());
                usersRepository.save(user);
                userVo.getPurchaseHistories().forEach((history) -> {
                    PurchaseHistory purchaseHistory = new PurchaseHistory();
                    purchaseHistory.setMaskName(history.getMaskName());
                    purchaseHistory.setTransactionAmount(history.getTransactionAmount());
                    purchaseHistory.setTransactionDate(history.getTransactionDate());
                    purchaseHistory.setUser(user);
                    purchaseHistory.setPharmacy(pharmacyRepository.getPharmacyByName(history.getPharmacyName()));
                    purchaseHistoryRepository.save(purchaseHistory);
                });
            });
            result.setStatus("InitData Success!");
        }catch (Exception e){
            System.out.println(e);
            result.setStatus("InitData Failed!");
        }
        return result;
    }

    @Override
    @Transactional
    public TransactionDetail transaction(TransactionRequest transactionRequest) {
        TransactionDetail transactionDetail = new TransactionDetail();
        // 取得使用者、藥局ID
        Users user = usersRepository.getUserByName(transactionRequest.getUserName());
        Pharmacy pharmacy = pharmacyRepository.getPharmacyByName(transactionRequest.getPharmacyName());
        Mask mask = maskRepository.getMaskByPharmacyNameAndMaskName(transactionRequest.getPharmacyName(), transactionRequest.getMaskName());

        // 判斷藥局是否有對應的口罩
        if(mask != null) {
            BigDecimal transactionAmount = mask.getPrice().multiply(transactionRequest.getNum());
            // 紀錄交易
            PurchaseHistory purchaseHistory = new PurchaseHistory();
            purchaseHistory.setMaskName(transactionRequest.getMaskName());
            purchaseHistory.setTransactionDate(new Date());
            purchaseHistory.setMaskName(transactionRequest.getMaskName());
            purchaseHistory.setUser(user);
            purchaseHistory.setPharmacy(pharmacy);
            purchaseHistory.setTransactionAmount(transactionAmount);
            purchaseHistoryRepository.save(purchaseHistory);
            // 藥局加錢
            transactionDetail.setPharmacyName(pharmacy.getName());
            transactionDetail.setMaskName(mask.getName());
            transactionDetail.setUserName(user.getName());
            transactionDetail.setTransactionAmount(transactionAmount);
            pharmacy.setCashBalance(pharmacy.getCashBalance().add(transactionDetail.getTransactionAmount()));
            // 個人扣錢
            user.setCashBalance(user.getCashBalance().subtract(transactionAmount));

            if(user.getCashBalance().compareTo(BigDecimal.ZERO) > 0) {
                usersRepository.save(user);
                pharmacyRepository.save(pharmacy);
                purchaseHistoryRepository.save(purchaseHistory);
                transactionDetail.setTransactionStatus("交易成功");
            }
            else{
                transactionDetail.setTransactionStatus("交易失敗，金額不足");
            }
        }
        else{
            transactionDetail.setTransactionStatus("該藥局沒有這款口罩");
        }
        return transactionDetail;
    }

    @Override
    public List<TopTransactionAmount> findHighTransactionAmountUser(QueryRequest queryRequest) {
        try{
            Date start = Util.transferDate(queryRequest.getStartTime());
            Date end = Util.transferDate(queryRequest.getEndTime());
            return purchaseHistoryRepository.findHighTransactionAmountUser(start, end, queryRequest.getRow());
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public RestBean<List<MaskTransaction>> queryPurchaseMaskByDate(QueryRequest queryRequest) {
        RestBean<List<MaskTransaction>> response = new RestBean<>();
        try {
            Date start = Util.transferDate(queryRequest.getStartTime());
            Date end = Util.transferDate(queryRequest.getEndTime());
            response.setBody(purchaseHistoryRepository.queryPurchaseMaskByDate(start, end));
            response.setStatus("queryPurchaseMaskByDate Success");
        }
        catch(Exception e){
            System.out.println(e);
            response.setStatus("queryPurchaseMaskByDate Failed");
        }
        return response;
    }

    @Override
    public List<String> searchMask(String item) {
        return maskRepository.search(item);
    }

    @Override
    public List<String> searchPharmacy(String item ) {
        return pharmacyRepository.search(item);
    }
}
