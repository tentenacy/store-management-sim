package com.tenutz.storemngsim.web.api.dto.optiongroup;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OptionGroupOptionMappersResponse {

    private List<OptionGroupOptionMapper> optionGroupOptionMappers;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class OptionGroupOptionMapper {

        private String storeCode;
        private String optionCode;
        private String optionName;
        private Integer price;
        private String use;
        private Integer priority;
    }
}
