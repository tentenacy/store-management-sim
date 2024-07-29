package com.tenutz.storemngsim.web.api.storemngsim.dto.menu;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MainMenuOptionGroupsResponse {

    private List<MainMenuOptionGroup> mainMenuOptionGroups;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class MainMenuOptionGroup {

        private String optionGroupCode;
        private String optionName;
        private boolean toggleSelect;
        private boolean required;
    }
}
