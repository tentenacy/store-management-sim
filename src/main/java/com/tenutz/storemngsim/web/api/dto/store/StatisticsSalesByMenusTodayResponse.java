package com.tenutz.storemngsim.web.api.dto.store;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StatisticsSalesByMenusTodayResponse {

    List<StatisticsSalesByMenusToday> contents;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class StatisticsSalesByMenusToday {
        private String menuName;
        private int amount;
        private int count;
        private float percent;

        public StatisticsSalesByMenusToday(String menuName, int amount, int count) {
            setMenuName(menuName);
            setAmount(amount);
            setCount(count);
            setPercent(0f);
        }
    }

    public StatisticsSalesByMenusTodayResponse(List<StatisticsSalesByMenusToday> list) {
        int total = 0;

        for (StatisticsSalesByMenusToday data : list) {
            total += data.getAmount();
        }

        for (StatisticsSalesByMenusToday data : list) {
            float percent = ((data.getAmount() * 1f) / (total * 1f)) * 100f;
            data.setPercent(percent);
        }

        setContents(list);
    }
}
