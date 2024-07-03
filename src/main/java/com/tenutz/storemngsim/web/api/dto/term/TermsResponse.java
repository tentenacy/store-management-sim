package com.tenutz.storemngsim.web.api.dto.term;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TermsResponse {

    private List<Term> terms;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Term {

        private String termCd;
        private String content;
        private Boolean use;
        private String createdAt;
        private String createdBy;
        private String openDate;
        private String closeDate;
    }
}
