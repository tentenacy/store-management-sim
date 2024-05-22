package com.tenutz.storemngsim.web.api.dto.category;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SubCategoryResponse {

    private String storeCode;
    private String mainCategoryCode;
    private String middleCategoryCode;
    private String categoryCode;
    private String categoryName;
    private boolean use;
    private Integer order;
    private String creator;
    private String createdAt;
    private String lastModifier;
    private String lastModifiedAt;
}
