package com.tenutz.storemngsim.web.api.dto.store;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StatisticsSalesByTimeResponse {

    private String dateHour;
    private int salesAmount;
    private int salesCount;
    private int cashSalesAmount;
    private int cashSalesCount;
    private int cashSalesCancelAmount;
    private int cashSalesCancelCount;
    private int creditCardSalesAmount;
    private int creditCardSalesCount;
    private int creditCardSalesCancelAmount;
    private int creditCardSalesCancelCount;
}
