package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.menu.Category;
import com.tenutz.storemngsim.domain.menu.CategoryRepository;
import com.tenutz.storemngsim.domain.sales.SalesMasterRepository;
import com.tenutz.storemngsim.domain.store.StoreMaster;
import com.tenutz.storemngsim.domain.store.StoreMasterRepository;
import com.tenutz.storemngsim.web.api.dto.common.CommonCondition;
import com.tenutz.storemngsim.web.api.dto.store.SalesRequest;
import com.tenutz.storemngsim.web.api.dto.store.SalesTotalResponse;
import com.tenutz.storemngsim.web.api.dto.store.StoreMainResponse;
import com.tenutz.storemngsim.web.api.dto.store.StoresResponse;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static com.tenutz.storemngsim.utils.TimeUtils.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {

    private final CategoryRepository categoryRepository;
    private final StoreMasterRepository storeMasterRepository;
    private final SalesMasterRepository salesMasterRepository;

    public StoresResponse stores(CommonCondition commonCond) {
        return new StoresResponse(storeMasterRepository.stores(commonCond).stream().map(store ->
            new StoresResponse.Store(
                    store.getStrCd(),
                    store.getStrNm()
            )
        ).collect(Collectors.toList()));
    }

    public StoreMainResponse main(String siteCd, String strCd) {

        StoreMaster foundStoreMaster = storeMasterRepository.findAllByStrCd(strCd).stream().findAny().orElseThrow(CEntityNotFoundException.CStoreMasterNotFoundException::new);

        Category foundMiddleCategory = categoryRepository.middleCategory(siteCd, strCd, "2000", "3000").orElseThrow(CEntityNotFoundException.CCategoryNotFoundException::new);

        SalesTotalResponse salesTotal = salesMasterRepository.totalSales(
                siteCd,
                strCd,
                new CommonCondition(
                        start(today()),
                        end(today()),
                        null,
                        null
                ),
                new SalesRequest()
        );

        return new StoreMainResponse(
                siteCd,
                strCd,
                foundStoreMaster.getStrNm(),
                foundStoreMaster.getStrMnger(),
                salesTotal.getTotalTake(),
                foundMiddleCategory.getCateCd1(),
                foundMiddleCategory.getCateCd2(),
                foundMiddleCategory.getImgName(),
                foundMiddleCategory.getImgUrl(),
                foundMiddleCategory.getPhoneNo(),
                foundMiddleCategory.getAddr(),
                foundMiddleCategory.getCreatedAt(),
                foundMiddleCategory.getUpdatedAt()
        );
    }
}
