package com.tenutz.storemngsim.web.api.storemngsim.dto.store;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String menuName;
    private String imageUrl;
    private String createdBy;
    private String createdAt;
    private String content;
    private int level;
    private int rating;
    private int sno;
    private MenuReviewReply menuReviewReply;

    @JsonIgnore
    private String imageName;

    public MenuReviewsResponse(long seq, String siteCode, String storeCode, String mainCategoryCode, String middleCategoryCode, String subCategoryCode, String menuCode, String menuName, String imageName, String createdBy, String createdAt, String content, int level, int rating, int sno, MenuReviewReply menuReviewReply) {
        this.seq = seq;
        this.siteCode = siteCode;
        this.storeCode = storeCode;
        this.mainCategoryCode = mainCategoryCode;
        this.middleCategoryCode = middleCategoryCode;
        this.subCategoryCode = subCategoryCode;
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.imageName = imageName;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.content = content;
        this.level = level;
        this.rating = rating;
        this.sno = sno;
        this.menuReviewReply = menuReviewReply;
    }

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
