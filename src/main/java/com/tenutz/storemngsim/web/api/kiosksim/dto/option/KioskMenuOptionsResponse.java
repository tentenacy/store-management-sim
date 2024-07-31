package com.tenutz.storemngsim.web.api.kiosksim.dto.option;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KioskMenuOptionsResponse {

    private List<MenuOptionGroup> menuOptionGroups;
    private String storeCode;
    private String mainCategoryCode;
    private String middleCategoryCode;
    private String subCategoryCode;
    private String menuCode;
    private String menuName;
    private int price;
    private int discountedPrice;
    private int additionalPackagingPrice;
    private String packaging;
    private boolean outOfStock;
    private String imageName;
    private String highlightType;
    private String eventDateFrom;
    private String eventDateTo;
    private String eventTimeFrom;
    private String eventTimeTo;
    private String eventDayOfWeek;

    private String imageUrl;

    public KioskMenuOptionsResponse(String storeCode, String mainCategoryCode, String middleCategoryCode, String subCategoryCode, String menuCode, String menuName, int price, int discountedPrice, int additionalPackagingPrice, String packaging, String outOfStock, String imageName, String highlightType, String eventDateFrom, String eventDateTo, String eventTimeFrom, String eventTimeTo, String eventDayOfWeek) {
        this.storeCode = storeCode;
        this.mainCategoryCode = mainCategoryCode;
        this.middleCategoryCode = middleCategoryCode;
        this.subCategoryCode = subCategoryCode;
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.additionalPackagingPrice = additionalPackagingPrice;
        this.packaging = packaging;
        this.outOfStock = soldOutYn(outOfStock);
        this.imageName = imageName;
        this.highlightType = highlightType;
        this.eventDateFrom = eventDateFrom;
        this.eventDateTo = eventDateTo;
        this.eventTimeFrom = eventTimeFrom;
        this.eventTimeTo = eventTimeTo;
        this.eventDayOfWeek = eventDayOfWeek;
    }

    public boolean soldOutYn(String soldoutYn) {
        return StringUtils.hasText(soldoutYn) && soldoutYn.equals("Y");
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class MenuOptionGroup {

        private List<OptionGroupOption> optionGroupOptions;
        private String optionGroupCode;
        private String optionGroupName;
        private boolean required;
        private Integer priority;
        private String creator;
        private String createdAt;
        private String lastModifier;
        private String lastModifiedAt;

        public MenuOptionGroup(List<OptionGroupOption> optionGroupOptions, String optionGroupCode, String optionGroupName, String required, Integer priority, String creator, String createdAt, String lastModifier, String lastModifiedAt) {
            this.optionGroupOptions = optionGroupOptions;
            this.optionGroupCode = optionGroupCode;
            this.optionGroupName = optionGroupName;
            this.required = mustSelectYn(required);
            this.priority = priority;
            this.creator = creator;
            this.createdAt = createdAt;
            this.lastModifier = lastModifier;
            this.lastModifiedAt = lastModifiedAt;
        }

        public boolean mustSelectYn(String mustSelectYn) {
            return StringUtils.hasText(mustSelectYn) && mustSelectYn.equals("Y");
        }

        @Data
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor
        public static class OptionGroupOption {

            private String optionCode;
            private String optionName;
            private int price;
        }
    }

}
