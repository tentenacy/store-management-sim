package com.tenutz.storemngsim.web.api.storemngsim.dto.term;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TermResponse {

    private String termCd;
    private String content;
    private Boolean use;
    private String createdAt;
    private String createdBy;
    private String openDate;
    private String closeDate;
}
