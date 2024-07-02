package com.tenutz.storemngsim.web.api.dto.help;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HelpCreateRequest {

    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
}
