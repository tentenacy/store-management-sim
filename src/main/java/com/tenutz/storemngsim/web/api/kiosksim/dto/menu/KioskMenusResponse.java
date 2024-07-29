package com.tenutz.storemngsim.web.api.kiosksim.dto.menu;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class KioskMenusResponse {

    private List<MenusCategory> menusCategories;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class MenusCategory {

        private List<CategoryMenu> categoryMenus;
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

        @Data
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class CategoryMenu {

            private String menuCode;
            private String menuName;
            private int price;
            private int discountedPrice;
            private int additionalPackagingPrice;
            private String packaging;
            private boolean outOfStock;
            private Boolean use;
//            private boolean ingredientDisplay;
            private String imageName;
            private String highlightType;
            private String showDateFrom;
            private String showDateTo;
            private String showTimeFrom;
            private String showTimeTo;
            private String showDayOfWeek;
            private String eventDateFrom;
            private String eventDateTo;
            private String eventTimeFrom;
            private String eventTimeTo;
            private String eventDayOfWeek;
            private String memoKor;
            private Integer priority;
//            private String ingredientDetails;

            private String imageUrl;

            public CategoryMenu(String menuCode, String menuName, int price, int discountedPrice, int additionalPackagingPrice, String packaging, String outOfStock, String use, String imageName, String highlightType, String showDateFrom, String showDateTo, String showTimeFrom, String showTimeTo, String showDayOfWeek, String eventDateFrom, String eventDateTo, String eventTimeFrom, String eventTimeTo, String eventDayOfWeek, String memoKor, Integer priority) {
                this.menuCode = menuCode;
                this.menuName = menuName;
                this.price = price;
                this.discountedPrice = discountedPrice;
                this.additionalPackagingPrice = additionalPackagingPrice;
                this.packaging = packaging;
                this.outOfStock = soldOutYn(outOfStock);
                this.use = useYn(use);
                this.imageName = imageName;
                this.highlightType = highlightType;
                this.showDateFrom = showDateFrom;
                this.showDateTo = showDateTo;
                this.showTimeFrom = showTimeFrom;
                this.showTimeTo = showTimeTo;
                this.showDayOfWeek = showDayOfWeek;
                this.eventDateFrom = eventDateFrom;
                this.eventDateTo = eventDateTo;
                this.eventTimeFrom = eventTimeFrom;
                this.eventTimeTo = eventTimeTo;
                this.eventDayOfWeek = eventDayOfWeek;
                this.memoKor = memoKor;
                this.priority = priority;
            }

            public Boolean useYn(String use) {
                if (!StringUtils.hasText(use) || use.equals("Y")) {
                    return true;
                } else if(use.equals("N")) {
                    return false;
                } else {
                    return null;
                }
            }

            public boolean soldOutYn(String soldoutYn) {
                return StringUtils.hasText(soldoutYn) && soldoutYn.equals("Y");
            }
        }
    }
}
