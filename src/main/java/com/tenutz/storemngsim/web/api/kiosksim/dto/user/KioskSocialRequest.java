package com.tenutz.storemngsim.web.api.kiosksim.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KioskSocialRequest {
    @NotEmpty
    private String accessToken;
}
