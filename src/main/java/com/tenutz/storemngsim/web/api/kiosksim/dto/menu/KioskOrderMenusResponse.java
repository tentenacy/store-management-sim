package com.tenutz.storemngsim.web.api.kiosksim.dto.menu;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class KioskOrderMenusResponse {

    private List<OrderMenu> orderMenus;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class OrderMenu {

        private String storeCode;
        private String mainCategoryCode;
        private String middleCategoryCode;
        private String categoryCode;
        private String categoryName;
        private String menuCode;
        private String menuName;
        private int price;
        private int discountedPrice;
        private String imageName;
        private String imageUrl;
        private String creator;
        private String createdAt;
        private String lastModifier;
        private String lastModifiedAt;

        public OrderMenu(String storeCode, String mainCategoryCode, String middleCategoryCode, String categoryCode, String categoryName, String menuCode, String menuName, int price, int discountedPrice, String imageName, String creator, String createdAt, String lastModifier, String lastModifiedAt) {
            this.storeCode = storeCode;
            this.mainCategoryCode = mainCategoryCode;
            this.middleCategoryCode = middleCategoryCode;
            this.categoryCode = categoryCode;
            this.categoryName = categoryName;
            this.menuCode = menuCode;
            this.menuName = menuName;
            this.price = price;
            this.discountedPrice = discountedPrice;
            this.imageName = imageName;
            this.creator = creator;
            this.createdAt = createdAt;
            this.lastModifier = lastModifier;
            this.lastModifiedAt = lastModifiedAt;
        }
    }
}
