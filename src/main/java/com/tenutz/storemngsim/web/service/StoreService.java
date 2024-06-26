package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.store.StoreMasterRepository;
import com.tenutz.storemngsim.web.api.dto.common.CommonCondition;
import com.tenutz.storemngsim.web.api.dto.store.StoresResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {

    private final StoreMasterRepository storeMasterRepository;

    public StoresResponse stores(CommonCondition commonCond) {
        return new StoresResponse(storeMasterRepository.stores(commonCond).stream().map(store ->
            new StoresResponse.Store(
                    store.getStrCd(),
                    store.getStrNm()
            )
        ).collect(Collectors.toList()));
    }
}
