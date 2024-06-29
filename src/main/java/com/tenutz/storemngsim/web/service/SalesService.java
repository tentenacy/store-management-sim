package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.sales.SalesMasterRepository;
import com.tenutz.storemngsim.domain.store.StoreMaster;
import com.tenutz.storemngsim.domain.store.StoreMasterRepository;
import com.tenutz.storemngsim.web.api.dto.common.CommonCondition;
import com.tenutz.storemngsim.web.api.dto.store.*;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException;
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

    public Page<SalesResponse> sales(String strCd, Pageable pageable, CommonCondition commonCond, SalesRequest request) {
        StoreMaster foundStoreMaster = storeMasterRepository.findAllByStrCd(strCd).stream().findAny().orElseThrow(CEntityNotFoundException.CStoreMasterNotFoundException::new);
        return salesMasterRepository.sales(foundStoreMaster.getSiteCd(), strCd, pageable, commonCond, request);
    }

    public SalesTotalResponse salesTotal(String strCd, CommonCondition commonCond, SalesRequest request) {
        StoreMaster foundStoreMaster = storeMasterRepository.findAllByStrCd(strCd).stream().findAny().orElseThrow(CEntityNotFoundException.CStoreMasterNotFoundException::new);
        return salesMasterRepository.totalSales(foundStoreMaster.getSiteCd(), strCd, commonCond, request);
    }

    public Page<StatisticsSaleByMenusResponse> statisticsSalesByMenu(String strCd, Pageable pageable, CommonCondition commonCond, StatisticsSaleByMenusRequest cond) {
        StoreMaster foundStoreMaster = storeMasterRepository.findAllByStrCd(strCd).stream().findAny().orElseThrow(CEntityNotFoundException.CStoreMasterNotFoundException::new);
        return salesMasterRepository.statisticsSalesByMenu(foundStoreMaster.getSiteCd(), strCd, pageable, commonCond, cond);
    }
}
