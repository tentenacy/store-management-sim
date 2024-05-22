package com.tenutz.storemngsim.web.api.dto.menu;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MenusResponse {

    private List<Menu> menus;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Menu {

        private String storeCode;
        private String mainCategoryCode;
        private String middleCategoryCode;
        private String subCategoryCode;
        private String menuCode;
        private String menuName;
        private int price;
        private int discountingPrice;
        private int discountedPrice;
        private boolean use;
    }
}
