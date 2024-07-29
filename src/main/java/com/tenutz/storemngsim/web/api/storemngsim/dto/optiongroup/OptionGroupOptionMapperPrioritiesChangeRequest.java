package com.tenutz.storemngsim.web.api.storemngsim.dto.optiongroup;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionGroupOptionMapperPrioritiesChangeRequest {

    @Valid
    @NotNull
//    @Size(min = 1)
    List<OptionGroupOption> optionGroupOptions;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OptionGroupOption {

        @NotEmpty
        private String optionCode;

        @NotNull
        private Integer priority;
    }
}
