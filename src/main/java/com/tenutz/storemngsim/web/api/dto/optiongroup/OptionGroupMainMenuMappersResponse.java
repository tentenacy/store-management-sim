package com.tenutz.storemngsim.web.api.dto.optiongroup;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OptionGroupMainMenuMappersResponse {

    private List<OptionGroupMainMenuMapper> optionGroupMainMenuMappers;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OptionGroupMainMenuMapper {

        private String storeCode;
        private String mainCategoryCode;
        private String middleCategoryCode;
        private String subCategoryCode;
        private String menuCode;
        private String menuName;
        private Integer price;
        private Boolean use;
        private Integer priority;

        public OptionGroupMainMenuMapper(String storeCode, String mainCategoryCode, String middleCategoryCode, String subCategoryCode, String menuCode, String menuName, Integer price, String use, Integer priority) {
            this.storeCode = storeCode;
            this.mainCategoryCode = mainCategoryCode;
            this.middleCategoryCode = middleCategoryCode;
            this.subCategoryCode = subCategoryCode;
            this.menuCode = menuCode;
            this.menuName = menuName;
            this.price = price;
            this.use = useYn(use);
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
    }
}
