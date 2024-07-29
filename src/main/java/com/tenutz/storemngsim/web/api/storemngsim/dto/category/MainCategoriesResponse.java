package com.tenutz.storemngsim.web.api.storemngsim.dto.category;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MainCategoriesResponse {

    private List<MainCategory> mainCategories;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class MainCategory {

        private String storeCode;
        private String categoryCode;
        private String categoryName;
        private boolean use;
        private Integer order;
        //        private String creator;
        private String createdAt;
        //        private String lastModifier;
        private String lastModifiedAt;
    }
}
