package com.tenutz.storemngsim.web.api.dto.store;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StatisticsSalesTotalByTimeResponse {

    private int salesAmountTotal;
    private int salesCountTotal;
    private int cashSalesAmountTotal;
    private int cashSalesCountTotal;
    private int cashSalesCancelAmountTotal;
    private int cashSalesCancelCountTotal;
    private int creditCardSalesAmountTotal;
    private int creditCardSalesCountTotal;
    private int creditCardSalesCancelAmountTotal;
    private int creditCardSalesCancelCountTotal;
}
