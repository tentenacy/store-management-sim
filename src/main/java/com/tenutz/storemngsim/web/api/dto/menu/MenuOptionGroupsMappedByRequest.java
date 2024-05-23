package com.tenutz.storemngsim.web.api.dto.menu;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuOptionGroupsMappedByRequest {

    @NotNull
    List<String> optionGroupCodes;
}
