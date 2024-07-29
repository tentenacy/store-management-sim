package com.tenutz.storemngsim.web.api.storemngsim.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreArgs {

    private String siteCd;
    private String strCd;
}
