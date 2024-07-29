package com.tenutz.storemngsim.web.api.storemngsim.dto.common;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionGroupsMappedByRequest {

    @NotNull
    List<String> optionGroupCodes;
}
