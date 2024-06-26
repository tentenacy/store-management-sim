package com.tenutz.storemngsim.web.api.dto.store;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EquipmentsResponse {

    private List<Equipment> equipments;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Equipment {
        private String storeCode;
        private String equipmentCode;
        private String equipmentType;
        private String equipmentName;
        private String equipmentIp;
        private Float partType;
        private String teamViewerNumber;
        private String anyDeskNumber;
        private String sshPort;
        private String gateway;
        private String netmask;
        private String dns1;
        private String dns2;
    }
}
