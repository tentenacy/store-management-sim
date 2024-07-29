package com.tenutz.storemngsim.web.api.storemngsim.dto.optiongroup;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionGroupMainMenuMappersDeleteRequest {

    @Valid
    @NotNull
    List<OptionGroupMainMenuMapperDelete> optionGroupMainMenus;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class OptionGroupMainMenuMapperDelete {

        @NotEmpty
        private String mainCategoryCode;
        @NotEmpty
        private String middleCategoryCode;
        @NotEmpty
        private String subCategoryCode;
        @NotEmpty
        private String menuCode;
    }
}
