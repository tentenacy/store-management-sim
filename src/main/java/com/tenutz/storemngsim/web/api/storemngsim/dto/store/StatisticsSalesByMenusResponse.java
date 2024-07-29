package com.tenutz.storemngsim.web.api.storemngsim.dto.store;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StatisticsSalesByMenusResponse {

    private String soldAt;
    private String menuName;
    private String categoryName;
    private String mainCategoryCode;
    private String middleCategoryCode;
    private String subCategoryCode;
    private String menuCode;
    private int cAuthAmount;
    private int cAuthCount;
    private int authAmount;
    private int authCount;
    private int authCAmount;
    private int authCCount;
}
