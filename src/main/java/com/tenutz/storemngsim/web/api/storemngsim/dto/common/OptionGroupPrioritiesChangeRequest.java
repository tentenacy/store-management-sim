package com.tenutz.storemngsim.web.api.storemngsim.dto.common;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionGroupPrioritiesChangeRequest {

    @Valid
    @NotNull
//    @Size(min = 1)
    List<OptionGroup> optionGroups;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OptionGroup {

        @NotEmpty
        private String optionGroupCode;

        @NotNull
        private Integer priority;
    }
}
