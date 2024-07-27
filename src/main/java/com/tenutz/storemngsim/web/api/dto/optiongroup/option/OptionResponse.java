package com.tenutz.storemngsim.web.api.dto.optiongroup.option;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OptionResponse {

    private String storeCode;
    private String optionCode;
    private String optionName;
    private int price;
    private Boolean use;
    private String imageName;
    private String imageUrl;
}
