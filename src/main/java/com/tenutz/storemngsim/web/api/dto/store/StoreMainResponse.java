package com.tenutz.storemngsim.web.api.dto.store;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreMainResponse {

    private String siteCode;
    private String storeCode;
    private String storeName;
    private String storeManagerName;
    private Integer todaySalesAmountTotal;
}
