package com.tenutz.storemngsim.web.api.dto.common;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainMenuSearchRequest {

    @NotEmpty
    private String mainCateCd;

    @NotEmpty
    private String middleCateCd;

    @NotEmpty
    private String subCateCd;
}
