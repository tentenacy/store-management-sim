package com.tenutz.storemngsim.web.api.kiosksim.controller;

import com.tenutz.storemngsim.web.api.common.dto.CommonCondition;
import com.tenutz.storemngsim.web.api.kiosksim.dto.review.KioskMenuReviewCreateRequest;
import com.tenutz.storemngsim.web.api.kiosksim.dto.review.KioskMenuReviewsResponse;
import com.tenutz.storemngsim.web.api.kiosksim.dto.review.KioskStoreReviewCreateRequest;
import com.tenutz.storemngsim.web.api.kiosksim.dto.review.KioskStoreReviewsResponse;
import com.tenutz.storemngsim.web.api.storemngsim.dto.user.StoreArgs;
import com.tenutz.storemngsim.web.service.ReviewService;
import com.tenutz.storemngsim.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public void createMenuReview(@PathVariable("kioskCode") String kioskCode, @Valid @RequestBody KioskMenuReviewCreateRequest request) {
        StoreArgs storeArgs = userService.storeArgs(kioskCode);
        reviewService.createKioskMenuReview(storeArgs.getSiteCd(), storeArgs.getStrCd(), request);
    }

    @PostMapping("/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStoreReview(@PathVariable("kioskCode") String kioskCode, @Valid @RequestBody KioskStoreReviewCreateRequest request) {
        StoreArgs storeArgs = userService.storeArgs(kioskCode);
        reviewService.createKioskStoreReview(storeArgs.getSiteCd(), storeArgs.getStrCd(), kioskCode, request);
    }

    @GetMapping("/main-menus/reviews")
    public Page<KioskMenuReviewsResponse> menuReviews(@PathVariable("kioskCode") String kioskCode, Pageable pageable, @Valid CommonCondition commonCond) {
        StoreArgs storeArgs = userService.storeArgs(kioskCode);
        return reviewService.kioskMenuReviews(storeArgs.getSiteCd(), storeArgs.getStrCd(), pageable, commonCond);
    }

    @GetMapping("/reviews")
    public Page<KioskStoreReviewsResponse> storeReviews(@PathVariable("kioskCode") String kioskCode, Pageable pageable, @Valid CommonCondition commonCond) {
        StoreArgs storeArgs = userService.storeArgs(kioskCode);
        return reviewService.kioskStoreReviews(storeArgs.getSiteCd(), storeArgs.getStrCd(), pageable, commonCond);
    }


}
