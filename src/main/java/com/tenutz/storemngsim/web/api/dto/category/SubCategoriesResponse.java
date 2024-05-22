package com.tenutz.storemngsim.web.api.dto.category;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SubCategoriesResponse {

    private List<SubCategory> subCategories;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class SubCategory {

        private String storeCode;
        private String mainCategoryCode;
        private String middleCategoryCode;
        private String categoryCode;
        private String categoryName;
        private boolean use;
        private Integer order;
        private String createdAt;
        private String lastModifiedAt;
    }
}
