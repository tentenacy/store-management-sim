package com.tenutz.storemngsim.web.api.dto.category;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainCategoryPrioritiesChangeRequest {

    @Valid
    @NotNull
//    @Size(min = 1)
    List<MainCategory> mainCategories;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MainCategory {

        @NotEmpty
        private String categoryCode;

        @NotNull
        private Integer priority;
    }
}
