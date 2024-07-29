package com.tenutz.storemngsim.web.api.storemngsim.dto.store;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StatisticsSalesTotalByMenusResponse {

    private int cAuthAmountTotal;
    private int cAuthCountTotal;
    private int authAmountTotal;
    private int authCountTotal;
    private int authCAmountTotal;
    private int authCCountTotal;
}
