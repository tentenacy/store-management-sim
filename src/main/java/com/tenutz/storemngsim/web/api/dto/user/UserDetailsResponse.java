package com.tenutz.storemngsim.web.api.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserDetailsResponse {

    private String seq;
    private String siteCode;
    private String storeCode;
    private String userId;
    private String username;
    private String provider;
    private String businessNumber;
    private String phoneNumber;
    private String storeName;
    private String address;
    private String kioskCode;
    private String registeredAt;
}
