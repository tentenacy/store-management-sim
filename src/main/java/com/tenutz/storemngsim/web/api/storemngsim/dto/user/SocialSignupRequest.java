package com.tenutz.storemngsim.web.api.storemngsim.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class SocialSignupRequest {
    @NotEmpty
    private String accessToken;
    @NotEmpty
    private String businessNumber;
    @NotEmpty
    private String phoneNumber;
    private String managerName;
    private String storeName;
    private String address;

    protected SocialSignupRequest() {
        managerName = "익명";
    }
}
