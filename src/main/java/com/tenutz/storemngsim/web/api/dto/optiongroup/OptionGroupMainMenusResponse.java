package com.tenutz.storemngsim.web.api.dto.optiongroup;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OptionGroupMainMenusResponse {

    private List<OptionGroupMainMenu> optionGroupMainMenus;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class OptionGroupMainMenu {

        private String storeCode;
        private String menuCode;
        private String menuName;
        private Integer price;
        private String mainCategoryCode;
        private String middleCategoryCode;
        private String subCategoryCode;
    }
}
