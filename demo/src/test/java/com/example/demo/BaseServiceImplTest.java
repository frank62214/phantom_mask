package com.example.demo;

import com.example.demo.bean.RestBean;
import com.example.demo.bean.TransactionRequest;
import com.example.demo.bean.converter.TransactionDetail;
import com.example.demo.entity.Mask;
import com.example.demo.entity.Pharmacy;
import com.example.demo.entity.PurchaseHistory;
import com.example.demo.entity.Users;
import com.example.demo.repository.*;
import com.example.demo.service.impl.BaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BaseServiceImplTest {

    @Mock
    private PharmacyRepository pharmacyRepository;

    @Mock
    private MaskRepository maskRepository;

    @Mock
    private OpeningHourRepository openingHourRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PurchaseHistoryRepository purchaseHistoryRepository;

    @InjectMocks
    private BaseServiceImpl baseService;

    @BeforeEach
    public void setup() {
        pharmacyRepository = Mockito.mock(PharmacyRepository.class);
        maskRepository = Mockito.mock(MaskRepository.class);
        openingHourRepository = Mockito.mock(OpeningHourRepository.class);
        usersRepository = Mockito.mock(UsersRepository.class);
        purchaseHistoryRepository = Mockito.mock(PurchaseHistoryRepository.class);

        baseService = new BaseServiceImpl(
                pharmacyRepository,
                maskRepository,
                openingHourRepository,
                usersRepository,
                purchaseHistoryRepository
        );
    }

    @Test
    public void testTransaction_Success() {
        // Setup data
        TransactionRequest request = new TransactionRequest();
        request.setUserName("Lester Arnold");
        request.setPharmacyName("First Care Rx");
        request.setMaskName("Second Smile (blue) (10 per pack)");
        request.setNum(new BigDecimal("2"));

        Users user = new Users();
        user.setName("Lester Arnold");
        user.setCashBalance(new BigDecimal("154.97"));

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setName("First Care Rx");
        pharmacy.setCashBalance(new BigDecimal("222.52"));

        Mask mask = new Mask();
        mask.setName("Second Smile (blue) (10 per pack)");
        mask.setPrice(new BigDecimal("49.83"));

        // Mock repositories
        when(usersRepository.getUserByName("Lester Arnold")).thenReturn(user);
        when(pharmacyRepository.getPharmacyByName("First Care Rx")).thenReturn(pharmacy);
        when(maskRepository.getMaskByPharmacyNameAndMaskName("First Care Rx", "Second Smile (blue) (10 per pack)")).thenReturn(mask);

        // Execute
        TransactionDetail result = baseService.transaction(request);

        // Verify
        assertEquals("交易成功", result.getTransactionStatus());
        assertEquals(new BigDecimal("99.66"), result.getTransactionAmount());
        assertEquals("Lester Arnold", result.getUserName());
        assertEquals("First Care Rx", result.getPharmacyName());
        assertEquals("Second Smile (blue) (10 per pack)", result.getMaskName());

        // Verify balance updates
        assertEquals(new BigDecimal("55.31"), user.getCashBalance());
        assertEquals(new BigDecimal("322.18"), pharmacy.getCashBalance());
    }
}
