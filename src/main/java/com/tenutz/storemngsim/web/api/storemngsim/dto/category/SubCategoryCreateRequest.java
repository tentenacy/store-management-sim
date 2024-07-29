package com.tenutz.storemngsim.web.api.storemngsim.dto.category;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubCategoryCreateRequest {

    @NotEmpty
    private String categoryCode;
    @NotEmpty
    private String categoryName;
    @NotNull
    private Boolean use;
}
