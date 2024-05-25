package com.tenutz.storemngsim.web.api.dto.category;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubCategoryUpdateRequest {

    @NotEmpty
    private String categoryName;
    @NotNull
    private Boolean use;
}
