package com.tenutz.storemngsim.web.api.dto.common;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionGroupsDeleteRequest {

    @NotNull
    private List<String> optionGroupCodes;
}
