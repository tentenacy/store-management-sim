package com.tenutz.storemngsim.web.api.storemngsim.dto.category;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MiddleCategoryResponse {

    private String storeCode;
    private String mainCategoryCode;
    private String categoryCode;
    private String categoryName;
    private boolean use;
    private String imageName;
    private String imageUrl;
    private Integer order;
    private String businessNumber;
    private String representativeName;
    private String tel;
    private String address;
    private String tid;
    private String creator;
    private String createdAt;
    private String lastModifier;
    private String lastModifiedAt;
}
