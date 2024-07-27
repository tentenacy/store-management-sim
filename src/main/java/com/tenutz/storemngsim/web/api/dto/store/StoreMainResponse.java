package com.tenutz.storemngsim.web.api.dto.store;

import com.tenutz.storemngsim.web.api.dto.category.MiddleCategoryResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreMainResponse {

    private String siteCode;
    private String storeCode;
    private String storeName;
    private String storeManagerName;
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
