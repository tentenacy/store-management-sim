package com.tenutz.storemngsim.web.api.kiosksim.controller;

import com.tenutz.storemngsim.web.api.kiosksim.dto.payment.KioskMenuPaymentCreateRequest;
import com.tenutz.storemngsim.web.api.kiosksim.dto.payment.KioskMenuPaymentCreateResponse;
import com.tenutz.storemngsim.web.api.storemngsim.dto.user.StoreArgs;
import com.tenutz.storemngsim.web.service.SalesService;
import com.tenutz.storemngsim.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/app/kiosk/{kioskCode}/users/main-menus")
@RequiredArgsConstructor
public class KioskMenuPaymentApiController {

    private final UserService userService;
    private final SalesService salesService;

    @PostMapping("/payments")
    @ResponseStatus(HttpStatus.CREATED)
    public KioskMenuPaymentCreateResponse create(@PathVariable("kioskCode") String kioskCode, @Valid @RequestBody KioskMenuPaymentCreateRequest request) {
        StoreArgs storeArgs = userService.storeArgs(kioskCode);
        if(request.getPaymentCode().equals("02")) {
            return salesService.createKioskMenusPaymentsByCreditCard(storeArgs.getSiteCd(), storeArgs.getStrCd(), kioskCode, request);
        } else {
            return salesService.createKioskMenusPaymentsByCashOrCoupon(storeArgs.getSiteCd(), storeArgs.getStrCd(), kioskCode, request);
        }
    }

    @DeleteMapping("/payments/call-numbers/{callNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("kioskCode") String kioskCode, @PathVariable("callNumber") String callNumber) {
        StoreArgs storeArgs = userService.storeArgs(kioskCode);
        salesService.deleteKioskMenusPayments(storeArgs.getSiteCd(), storeArgs.getStrCd(), kioskCode, callNumber);
    }
}
