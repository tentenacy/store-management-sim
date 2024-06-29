package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.store.StoreMasterRepository;
import com.tenutz.storemngsim.utils.EntityUtils;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final StoreMasterRepository storeMasterRepository;

    public String storeCode() {
        return storeMasterRepository.findByBusinessNumber(EntityUtils.userThrowable().getBusinessNo())
                .orElseThrow(CEntityNotFoundException.CStoreMasterNotFoundException::new).getStrCd();
    }
}
