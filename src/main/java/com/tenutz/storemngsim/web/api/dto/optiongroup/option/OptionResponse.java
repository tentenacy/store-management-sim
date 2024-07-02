package com.tenutz.storemngsim.web.api.dto.optiongroup.option;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OptionResponse {

    private String storeCode;
    private String optionCode;
    private String optionName;
    private int price;
    private int discountedPrice;
    private int additionalPackagingPrice;
    private String packaging;
    private boolean outOfStock;
    private boolean use;
    private String imageName;
    private String imageUrl;
    private String optionNameKor;
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
}
