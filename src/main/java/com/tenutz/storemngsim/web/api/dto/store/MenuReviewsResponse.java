package com.tenutz.storemngsim.web.api.dto.store;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MenuReviewsResponse {

    private long seq;
    private String siteCode;
    private String storeCode;
    private String mainCategoryCode;
    private String middleCategoryCode;
    private String subCategoryCode;
    private String menuCode;
    private String createdBy;
    private String createdAt;
    private String content;
    private int keyword;
    private int level;
    private int rating;
    private int sno;
    private MenuReviewReply menuReviewReply;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class MenuReviewReply {

        private long seq;
        private String siteCode;
        private String storeCode;
        private String mainCategoryCode;
        private String middleCategoryCode;
        private String subCategoryCode;
        private String menuCode;
        private String createdBy;
        private String createdAt;
        private String content;
    }
}
