package com.tenutz.storemngsim.web.api.dto.optiongroup.option;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OptionOptionGroupsResponse {

    private List<OptionOptionGroup> optionOptionGroups;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class OptionOptionGroup {

        private String optionGroupCode;
        private String optionName;
        private boolean toggleSelect;
        private boolean required;
    }
}
