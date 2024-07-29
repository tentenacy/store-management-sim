package com.tenutz.storemngsim.web.api.storemngsim.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserUpdateRequest {

    @NotEmpty
    private String businessNumber;

    @JsonProperty(value = "ownerName")
    private String username;

    @NotEmpty
    private String phoneNumber;

    private String storeName;

    private String address;
}
