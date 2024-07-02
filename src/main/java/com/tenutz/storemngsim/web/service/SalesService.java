package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.customer.StoreReviewRepository;
import com.tenutz.storemngsim.domain.sales.SalesMasterRepository;
import com.tenutz.storemngsim.domain.store.StoreMasterRepository;
import com.tenutz.storemngsim.web.api.dto.common.CommonCondition;
import com.tenutz.storemngsim.web.api.dto.store.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SalesService {

    private final SalesMasterRepository salesMasterRepository;
    private final StoreMasterRepository storeMasterRepository;
    private final StoreReviewRepository storeReviewRepository;

    public Page<SalesResponse> sales(String siteCd, String strCd, Pageable pageable, CommonCondition commonCond, SalesRequest request) {
        return salesMasterRepository.sales(siteCd, strCd, pageable, commonCond, request);
    }

    public SalesTotalResponse salesTotal(String siteCd, String strCd, CommonCondition commonCond, SalesRequest request) {
        return salesMasterRepository.totalSales(siteCd, strCd, commonCond, request);
    }

    public Page<StatisticsSalesByMenusResponse> statisticsSalesByMenu(String siteCd, String strCd, Pageable pageable, CommonCondition commonCond, StatisticsSaleByMenusRequest cond) {
        return salesMasterRepository.statisticsSalesByMenu(siteCd, strCd, pageable, commonCond, cond);
    }

    public StatisticsSalesTotalByMenusResponse statisticsSalesTotalByMenu(String siteCd, String strCd, Pageable pageable, CommonCondition commonCond, StatisticsSaleByMenusRequest cond) {
        return salesMasterRepository.statisticsSalesTotalByMenu(siteCd, strCd, pageable, commonCond, cond);
    }

    public StatisticsSalesByCreditCardResponse statisticsSalesByCreditCard(String siteCd, String strCd, CommonCondition commonCond) {
        return salesMasterRepository.statisticsSalesByCreditCard(siteCd, strCd, commonCond);
    }

    public StatisticsSalesTotalByCreditCardResponse statisticsSalesTotalByCreditCard(String siteCd, String strCd, CommonCondition commonCond) {
        return salesMasterRepository.statisticsSalesTotalByCreditCard(siteCd, strCd, commonCond);
    }

    public StatisticsSalesByTimeResponse statisticsSalesByTime(String siteCd, String strCd, Pageable pageable, CommonCondition commonCond) {
        return salesMasterRepository.statisticsSalesByTime(siteCd, strCd, pageable, commonCond);
    }

    public StatisticsSalesTotalByTimeResponse statisticsSalesTotalByTime(String siteCd, String strCd, CommonCondition commonCond) {
        return salesMasterRepository.statisticsSalesTotalByTime(siteCd, strCd, commonCond);
    }
}
