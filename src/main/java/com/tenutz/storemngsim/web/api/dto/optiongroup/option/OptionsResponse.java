package com.tenutz.storemngsim.web.api.dto.optiongroup.option;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OptionsResponse {

    private List<Option> options;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Option {

        private String storeCode;
        private String optionCode;
        private String optionName;
        private String imageUrl;
        private boolean outOfStock;
        private int price;
        private int discountingPrice;
        private int discountedPrice;
        private Boolean use;
    }
}
