package com.tenutz.storemngsim.web.api.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
