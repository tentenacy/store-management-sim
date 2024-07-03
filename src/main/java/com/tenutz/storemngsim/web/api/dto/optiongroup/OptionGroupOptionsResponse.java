package com.tenutz.storemngsim.web.api.dto.optiongroup;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OptionGroupOptionsResponse {

    private List<OptionGroupOption> optionGroupOptions;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class OptionGroupOption {

        private String storeCode;
        private String optionCode;
        private String optionName;
        private String imageUrl;
        private boolean outOfStock;
        private Integer price;
        private int discountingPrice;
        private int discountedPrice;
        private Boolean use;
    }
}
