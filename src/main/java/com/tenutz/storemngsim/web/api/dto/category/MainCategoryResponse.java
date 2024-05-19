package com.tenutz.storemngsim.web.api.dto.category;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MainCategoryResponse {

    private String storeCode;
    private String categoryCode;
    private String categoryName;
    private boolean use;
    private Integer order;
    private String creator;
    private String createdAt;
    private String lastModifier;
    private String lastModifiedAt;
}
