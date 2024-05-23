package com.tenutz.storemngsim.web.api.dto.option;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionUpdateRequest {

    @NotEmpty
    private String optionName;
    @NotNull
    private Integer price;
    private Integer discountedPrice;
    private Integer additionalPackagingPrice;
    @NotEmpty
    private String packaging;
    @NotNull
    private Boolean outOfStock;
    @NotNull
    private Boolean use;
    private String imageName;
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
