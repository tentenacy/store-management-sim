package com.tenutz.storemngsim.web.api.kiosksim.controller;

import com.tenutz.storemngsim.web.api.kiosksim.dto.payment.KioskMenuPaymentCreateRequest;
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
    public void create(@PathVariable("kioskCode") String kioskCode, @Valid @RequestBody KioskMenuPaymentCreateRequest request) {
        StoreArgs storeArgs = userService.storeArgs(kioskCode);
        salesService.createKioskMenusPayments(storeArgs.getSiteCd(), storeArgs.getStrCd(), request);
    }
}
