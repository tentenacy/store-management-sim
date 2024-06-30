package com.tenutz.storemngsim.web.api.dto.store;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreReviewsResponse {

    private long seq;
    private String siteCode;
    private String storeCode;
    private String middleCategoryCode;
    private String createdBy;
    private String createdAt;
    private String content;
    private int keyword;
    private int level;
    private int rating;
    private long sno;
    private StoreReviewReply storeReviewReply;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class StoreReviewReply {

        private long seq;
        private String siteCode;
        private String storeCode;
        private String middleCategoryCode;
        private String createdBy;
        private String createdAt;
        private String content;
    }
}
