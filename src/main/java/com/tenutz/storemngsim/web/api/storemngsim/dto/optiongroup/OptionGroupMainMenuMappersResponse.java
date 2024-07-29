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
        private String imageUrl;
        private boolean outOfStock;
        private Integer price;
        private int discountingPrice;
        private int discountedPrice;
        private Boolean use;
        private Integer priority;

        @JsonIgnore
        private String imageName;

        public OptionGroupMainMenuMapper(String storeCode, String mainCategoryCode, String middleCategoryCode, String subCategoryCode, String menuCode, String menuName, String imageName, String outOfStock, Integer price, int discountingPrice, int discountedPrice, String use, Integer priority) {
            this.storeCode = storeCode;
            this.mainCategoryCode = mainCategoryCode;
            this.middleCategoryCode = middleCategoryCode;
            this.subCategoryCode = subCategoryCode;
            this.menuCode = menuCode;
            this.menuName = menuName;
            this.imageName = imageName;
            this.outOfStock = soldOutYn(outOfStock);
            this.price = price;
            this.discountingPrice = discountingPrice;
            this.discountedPrice = discountedPrice;
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

        public boolean soldOutYn(String soldoutYn) {
            return StringUtils.hasText(soldoutYn) && soldoutYn.equals("Y");
        }
    }
}
