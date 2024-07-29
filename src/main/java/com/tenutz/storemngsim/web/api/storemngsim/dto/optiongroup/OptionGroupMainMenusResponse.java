package com.tenutz.storemngsim.web.api.storemngsim.dto.optiongroup;

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
public class OptionGroupMainMenusResponse {

    private List<OptionGroupMainMenu> optionGroupMainMenus;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class OptionGroupMainMenu {

        private String storeCode;
        private String menuCode;
        private String menuName;
        private String imageUrl;
        private boolean outOfStock;
        private Integer price;
        private int discountingPrice;
        private int discountedPrice;
        private Boolean use;
        private String mainCategoryCode;
        private String middleCategoryCode;
        private String subCategoryCode;
        private String mainCategoryName;
        private String middleCategoryName;
        private String subCategoryName;

        public OptionGroupMainMenu(String storeCode, String menuCode, String menuName, String imageName, String outOfStock, Integer price, int discountingPrice, int discountedPrice, String use, String mainCategoryCode, String middleCategoryCode, String subCategoryCode, String mainCategoryName, String middleCategoryName, String subCategoryName) {
            this.storeCode = storeCode;
            this.menuCode = menuCode;
            this.menuName = menuName;
            this.imageName = imageName;
            this.outOfStock = soldOutYn(outOfStock);
            this.price = price;
            this.discountingPrice = discountingPrice;
            this.discountedPrice = discountedPrice;
            this.use = useYn(use);
            this.mainCategoryCode = mainCategoryCode;
            this.middleCategoryCode = middleCategoryCode;
            this.subCategoryCode = subCategoryCode;
            this.mainCategoryName = mainCategoryName;
            this.middleCategoryName = middleCategoryName;
            this.subCategoryName = subCategoryName;
        }

        @JsonIgnore
        private String imageName;

        public Boolean useYn(String useYn) {
            if (!StringUtils.hasText(useYn) || useYn.equals("Y")) {
                return true;
            } else if(useYn.equals("N")) {
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
