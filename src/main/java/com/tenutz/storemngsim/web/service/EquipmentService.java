package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.store.EquipmentMasterRepository;
import com.tenutz.storemngsim.web.api.dto.store.EquipmentsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentMasterRepository equipmentMasterRepository;

    public EquipmentsResponse equipments(String strCd) {
        return new EquipmentsResponse(equipmentMasterRepository.equipments(strCd).stream().map(em ->
            new EquipmentsResponse.Equipment(
                    em.getStrCd(),
                    em.getEquCd(),
                    em.getEquType(),
                    em.getEquNm(),
                    em.getEquIp(),
                    em.getPartCd(),
                    em.getTeamviewId(),
                    em.getAnydeskId(),
                    em.getSshPort(),
                    em.getGateway(),
                    em.getNetmask(),
                    em.getDns1(),
                    em.getDns2()
            )
        ).collect(Collectors.toList()));
    }
}
