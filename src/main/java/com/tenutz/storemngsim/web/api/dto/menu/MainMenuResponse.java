package com.tenutz.storemngsim.web.api.dto.menu;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MainMenuResponse {

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
    private boolean use;
    private boolean ingredientDisplay;
    private String imageName;
    private String mainMenuNameKor;
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
    private String ingredientDetails;
}
