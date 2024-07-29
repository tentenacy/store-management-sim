package com.tenutz.storemngsim.web.api.storemngsim.dto.store;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StatisticsSalesByCreditCardResponse {

    List<StatisticsSalesByCreditCard> contents;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class StatisticsSalesByCreditCard {

        private String date;
        private String creditCardCode;
        private String creditCardCompany;
        private int salesAmount;
        private int salesCount;
        private int creditCardSalesAmount;
        private int creditCardSalesCount;
        private int creditCardSalesCancelAmount;
        private int creditCardSalesCancelCount;
    }
}
