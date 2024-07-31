package com.tenutz.storemngsim.web.api.storemngsim.controller;

import com.tenutz.storemngsim.web.api.common.dto.CommonCondition;
import com.tenutz.storemngsim.web.api.storemngsim.dto.store.*;
import com.tenutz.storemngsim.web.api.storemngsim.dto.user.StoreArgs;
import com.tenutz.storemngsim.web.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreApiController {

    private final UserService userService;
    private final SalesService salesService;
    private final ReviewService reviewService;
    private final StoreService storeService;
    private final CategoryService categoryService;

/*
    @GetMapping
    public StoresResponse stores(@Valid CommonCondition commonCond) {
        return storeService.stores(commonCond);
    }
*/

    /**
     * 매출조회(페이징)
     * @param pageable
     * @param commonCond
     * @param request
     * @return
     */
    @GetMapping("/sales")
    public Page<SalesResponse> sales(Pageable pageable, @Valid CommonCondition commonCond, @Valid SalesRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        return salesService.sales(storeArgs.getSiteCd(), storeArgs.getStrCd(), pageable, commonCond, request);
    }

    /**
     * 매출총계
     * @param commonCond
     * @param request
     * @return
     */
    @GetMapping("/sales/total")
    public SalesTotalResponse salesTotal(@Valid CommonCondition commonCond, @Valid SalesRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        return salesService.salesTotal(storeArgs.getSiteCd(), storeArgs.getStrCd(), commonCond, request);
    }

    /**
     * 메뉴별판매집계(페이징)
     * @param pageable
     * @param commonCond
     * @param cond
     * @return
     */
    @GetMapping("/statistics/sales-by-menu")
    public Page<StatisticsSalesByMenusResponse> statisticsSalesByMenu(Pageable pageable, @Valid CommonCondition commonCond, @Valid StatisticsSaleByMenusRequest cond) {
        StoreArgs storeArgs = userService.storeArgs();
        return salesService.statisticsSalesByMenu(storeArgs.getSiteCd(), storeArgs.getStrCd(), pageable, commonCond, cond);
    }

    /**
     * 메뉴별판매총계
     * @param pageable
     * @param commonCond
     * @param cond
     * @return
     */
    @GetMapping("/statistics/sales-by-menu/total")
    public StatisticsSalesTotalByMenusResponse statisticsSalesTotalByMenu(Pageable pageable, @Valid CommonCondition commonCond, @Valid StatisticsSaleByMenusRequest cond) {
        StoreArgs storeArgs = userService.storeArgs();
        return salesService.statisticsSalesTotalByMenu(storeArgs.getSiteCd(), storeArgs.getStrCd(), pageable, commonCond, cond);
    }

    /**
     * 당일 메뉴별 판매현황
     * @return
     */
    @GetMapping("/statistics/sales-by-menu/today")
    public StatisticsSalesByMenusTodayResponse statisticsSalesByMenusToday() {
        StoreArgs storeArgs = userService.storeArgs();
        return salesService.statisticsSalesByMenusToday(storeArgs.getSiteCd(), storeArgs.getStrCd());
    }

    /**
     * 카드사별판매집계
     * @param commonCond
     * @return
     */
    @GetMapping("/statistics/sales-by-card")
    public StatisticsSalesByCreditCardResponse statisticsSalesByCreditCard(@Valid CommonCondition commonCond) {
        StoreArgs storeArgs = userService.storeArgs();
        return salesService.statisticsSalesByCreditCard(storeArgs.getSiteCd(), storeArgs.getStrCd(), commonCond);
    }

    /**
     * 카드사별판매총계
     * @param commonCond
     * @return
     */
    @GetMapping("/statistics/sales-by-card/total")
    public StatisticsSalesTotalByCreditCardResponse statisticsSalesTotalByCreditCard(@Valid CommonCondition commonCond) {
        StoreArgs storeArgs = userService.storeArgs();
        return salesService.statisticsSalesTotalByCreditCard(storeArgs.getSiteCd(), storeArgs.getStrCd(), commonCond);
    }

    /**
     * 시간대별판매집계
     * @param pageable
     * @param commonCond
     * @return
     */
    @GetMapping("/statistics/sales-by-time")
    public StatisticsSalesByTimeResponse statisticsSalesByTime(Pageable pageable, @Valid CommonCondition commonCond) {
        StoreArgs storeArgs = userService.storeArgs();
        return salesService.statisticsSalesByTime(storeArgs.getSiteCd(), storeArgs.getStrCd(), pageable, commonCond);
    }

    /**
     * 시간대별판매총계
     * @param commonCond
     * @return
     */
    @GetMapping("/statistics/sales-by-time/total")
    public StatisticsSalesTotalByTimeResponse statisticsSalesTotalByTime(@Valid CommonCondition commonCond) {
        StoreArgs storeArgs = userService.storeArgs();
        return salesService.statisticsSalesTotalByTime(storeArgs.getSiteCd(), storeArgs.getStrCd(), commonCond);
    }

    /**
     * 가맹점평가조회(페이징)
     * @param pageable
     * @param commonCond
     * @return
     */
    @GetMapping("/reviews")
    public Page<StoreReviewsResponse> storeReviews(Pageable pageable, @Valid CommonCondition commonCond) {
        StoreArgs storeArgs = userService.storeArgs();
        return reviewService.storeReviews(storeArgs.getSiteCd(), storeArgs.getStrCd(), pageable, commonCond);
    }

    /**
     * 가맹점평가답글등록
     * @param reviewSeq 리뷰시퀀스
     * @param request
     */
    @PostMapping("/reviews/{reviewSeq}/replies")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStoreReviewReply(@PathVariable Long reviewSeq, @Valid @RequestBody ReviewReplyCreateRequest request) {
        reviewService.createStoreReviewReply(reviewSeq, request);
    }

    /**
     * 가맹점평가답글수정
     * @param replySeq
     * @param request
     */
    @PutMapping("/reviews/replies/{replySeq}")
    public void updateStoreReviewReply(@PathVariable Long replySeq, @Valid @RequestBody ReviewReplyUpdateRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        reviewService.updateStoreReviewReply(replySeq, storeArgs.getSiteCd(), storeArgs.getStrCd(), request);
    }

    /**
     * 가맹점평가답글삭제
     * @param replySeq
     */
    @DeleteMapping("/reviews/replies/{replySeq}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStoreReviewReply(@PathVariable Long replySeq) {
        StoreArgs storeArgs = userService.storeArgs();
        reviewService.deleteStoreReviewReply(replySeq, storeArgs.getSiteCd(), storeArgs.getStrCd());
    }

    /**
     * 메뉴평가조회(페이징)
     * @param pageable
     * @param commonCond
     * @return
     */
    @GetMapping("/menus/reviews")
    public Page<MenuReviewsResponse> menuReviews(Pageable pageable, @Valid CommonCondition commonCond) {
        StoreArgs storeArgs = userService.storeArgs();
        return reviewService.menuReviews(storeArgs.getSiteCd(), storeArgs.getStrCd(), pageable, commonCond);
    }

    /**
     * 메뉴평가답글등록
     * @param reviewSeq
     * @param request
     */
    @PostMapping("/menus/reviews/{reviewSeq}/replies")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMenuReviewReply(@PathVariable Long reviewSeq, @Valid @RequestBody ReviewReplyCreateRequest request) {
        reviewService.createMenuReviewReply(reviewSeq, request);
    }

    /**
     * 메뉴평가답글수정
     * @param replySeq
     * @param request
     */
    @PutMapping("/menus/reviews/replies/{replySeq}")
    public void updateMenuReviewReply(@PathVariable Long replySeq, @Valid @RequestBody ReviewReplyUpdateRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        reviewService.updateMenuReviewReply(replySeq, storeArgs.getSiteCd(), storeArgs.getStrCd(), request);
    }

    /**
     * 메뉴평가답글삭제
     * @param replySeq
     */
    @DeleteMapping("/menus/reviews/replies/{replySeq}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuReviewReply(@PathVariable Long replySeq) {
        StoreArgs storeArgs = userService.storeArgs();
        reviewService.deleteMenuReviewReply(replySeq, storeArgs.getSiteCd(), storeArgs.getStrCd());
    }

    @GetMapping("/main")
    public StoreMainResponse main() {
        StoreArgs storeArgs = userService.storeArgs();
        return storeService.main(storeArgs.getSiteCd(), storeArgs.getStrCd());
    }

    /**
     * 메뉴평가동기화(테스트용)
     */
    @PostMapping("/menus/reviews/sync")
    public void updateAsMenu() {
        reviewService.updateAsMenu();
    }

    /**
     * 가맹점평가동기화(테스트용)
     */
    @PostMapping("/reviews/sync")
    public void updateAsStore() {
        reviewService.updateAsStore();
    }
}