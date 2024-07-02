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
    private String middleCategoryName;
    private String createdBy;
    private String createdAt;
    private String content;
    private int keyword;
    private int level;
    private int rating;
    private long sno;
    private StoreReviewReply storeReviewReply;

    public StoreReviewsResponse(long seq, String siteCode, String storeCode, String middleCategoryCode, String createdBy, String createdAt, String content, int keyword, int level, int rating, long sno, StoreReviewReply storeReviewReply) {
        this.seq = seq;
        this.siteCode = siteCode;
        this.storeCode = storeCode;
        this.middleCategoryCode = middleCategoryCode;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.content = content;
        this.keyword = keyword;
        this.level = level;
        this.rating = rating;
        this.sno = sno;
        this.storeReviewReply = storeReviewReply;
    }

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
