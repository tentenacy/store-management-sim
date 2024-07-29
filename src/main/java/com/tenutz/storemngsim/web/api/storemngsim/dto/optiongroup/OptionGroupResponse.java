package com.tenutz.storemngsim.web.api.storemngsim.dto.optiongroup;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OptionGroupResponse {

    private String storeCode;
    private String optionGroupCode;
    private String optionGroupName;
    private boolean toggleSelect;
    private boolean required;
    private boolean use;
    private Integer priority;
    private String creator;
    private String createdAt;
    private String lastModifier;
    private String lastModifiedAt;
}
