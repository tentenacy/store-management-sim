package com.tenutz.storemngsim.web.api.dto.help;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HelpsResponse {

    private List<Help> helps;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Help {

        private Integer seq;
        private String title;
        private String content;
        private String imageName;
        private String imageUrl;
        private String createdAt;
        private String createdBy;
        private String createdIp;
        private String updatedAt;
        private String updatedBy;
        private String updatedIp;
    }
}
