package com.tenutz.storemngsim.web.api.dto.menu;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MainMenuMappersResponse {

    private List<MainMenuMapper> mainMenuMappers;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class MainMenuMapper {

        private String optionGroupCode;
        private String optionName;
        private Integer priority;
    }
}
