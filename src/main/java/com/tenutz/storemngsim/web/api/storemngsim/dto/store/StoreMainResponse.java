package com.tenutz.storemngsim.web.api.storemngsim.dto.store;

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
    private String kioskCode;
    private Integer todaySalesAmountTotal;

    private String mainCategoryCode;
    private String middleCategoryCode;
    private String imageName;
    private String imageUrl;
    private String tel;
    private String address;
    private String createdAt;
    private String lastModifiedAt;
}
