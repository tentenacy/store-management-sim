package com.tenutz.storemngsim.web.api.storemngsim.dto.store;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StatisticsSalesByTimeResponse {

    List<StatisticsSalesByTime> contents;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class StatisticsSalesByTime {
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
}
