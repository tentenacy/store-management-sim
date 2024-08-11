package com.tenutz.storemngsim.web.api.kiosksim.dto.review;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class KioskMenuReviewCreateRequest {

    @NotEmpty
    private String mainCategoryCode;
    @NotEmpty
    private String middleCategoryCode;
    @NotEmpty
    private String subCategoryCode;
    @NotEmpty
    private String menuCode;
    @NotEmpty
    private String name;
    @NotEmpty
    private String contents;
    @NotNull
    private Integer rating;
}
