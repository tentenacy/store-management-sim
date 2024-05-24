package com.tenutz.storemngsim.web.api.dto.optiongroup;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionGroupMainMenuMappersDeleteRequest {

    @NotNull
    List<OptionGroupMainMenuMapperDelete> optionGroupMainMenus;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class OptionGroupMainMenuMapperDelete {

        private String mainCategoryCode;
        private String middleCategoryCode;
        private String subCategoryCode;
        private String menuCode;
    }
}
