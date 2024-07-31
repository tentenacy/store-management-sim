package com.tenutz.storemngsim.web.api.kiosksim.controller;

import com.tenutz.storemngsim.web.api.kiosksim.dto.review.MenuReviewCreateRequest;
import com.tenutz.storemngsim.web.api.kiosksim.dto.review.StoreReviewCreateRequest;
import com.tenutz.storemngsim.web.api.storemngsim.dto.user.StoreArgs;
import com.tenutz.storemngsim.web.service.ReviewService;
import com.tenutz.storemngsim.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/app/kiosk/{kioskCode}")
@RequiredArgsConstructor
public class KioskReviewApiController {

    private final UserService userService;
    private final ReviewService reviewService;

    @PostMapping("/main-menus/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMenuReview(@PathVariable("kioskCode") String kioskCode, @Valid @RequestBody MenuReviewCreateRequest request) {
        StoreArgs storeArgs = userService.storeArgs(kioskCode);
        reviewService.createKioskMenuReview(storeArgs.getSiteCd(), storeArgs.getStrCd(), request);
    }

    @PostMapping("/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStoreReview(@PathVariable("kioskCode") String kioskCode, @Valid @RequestBody StoreReviewCreateRequest request) {
        StoreArgs storeArgs = userService.storeArgs(kioskCode);
        reviewService.createKioskStoreReview(storeArgs.getSiteCd(), storeArgs.getStrCd(), kioskCode, request);
    }
}
