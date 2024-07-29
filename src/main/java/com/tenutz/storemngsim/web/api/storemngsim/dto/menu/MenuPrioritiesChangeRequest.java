package com.tenutz.storemngsim.web.api.storemngsim.dto.menu;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuPrioritiesChangeRequest {

    @Valid
    @NotNull
//    @Size(min = 1)
    List<MainCategory> menus;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MainCategory {

        @NotEmpty
        private String menuCode;

        @NotNull
        private Integer priority;
    }
}
