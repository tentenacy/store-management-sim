package com.tenutz.storemngsim.web.api.dto.optiongroup;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionGroupMainMenuMapperPrioritiesChangeRequest {

    @Valid
    @NotNull
//    @Size(min = 1)
    List<OptionGroupMainMenu> optionGroupMainMenus;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OptionGroupMainMenu {

        @NotEmpty
        private String mainCategoryCode;
        @NotEmpty
        private String middleCategoryCode;
        @NotEmpty
        private String subCategoryCode;
        @NotEmpty
        private String menuCode;

        @NotNull
        private Integer priority;
    }
}
