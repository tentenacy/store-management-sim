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
@AllArgsConstructor
public class MainMenusMappedByRequest {

    @Valid
    @NotNull
    List<MainMenuMappedBy> mainMenusMappedBy;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class MainMenuMappedBy {

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
