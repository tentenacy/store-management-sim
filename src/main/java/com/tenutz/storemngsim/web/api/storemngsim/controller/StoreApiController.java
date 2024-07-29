package com.tenutz.storemngsim.web.api.controller;

import com.tenutz.storemngsim.web.api.dto.common.CommonCondition;
import com.tenutz.storemngsim.web.api.dto.store.EquipmentsResponse;
import com.tenutz.storemngsim.web.api.dto.store.StoresResponse;
import com.tenutz.storemngsim.web.service.EquipmentService;
import com.tenutz.storemngsim.web.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreApiController {

    private final EquipmentService equipmentService;
    private final StoreService storeService;

    /**
     * 장비조회
     * @param strCd 가맹점코드
     * @return
     */
    @GetMapping("/{strCd}/equipments")
    public EquipmentsResponse equipments(@PathVariable String strCd) {
        return equipmentService.equipments(strCd);
    }

    /**
     * 가맹점조회
     * @param commonCond
     * @return
     */
    @GetMapping
    public StoresResponse stores(@Valid CommonCondition commonCond) {
        return storeService.stores(commonCond);
    }
}
