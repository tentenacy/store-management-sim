package com.tenutz.storemngsim.web.api.storemngsim.dto.optiongroup;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionGroupUpdateRequest {

    @NotEmpty
    private String optionGroupName;
    @NotNull
    private Boolean toggleSelect;
    @NotNull
    private Boolean required;
}
