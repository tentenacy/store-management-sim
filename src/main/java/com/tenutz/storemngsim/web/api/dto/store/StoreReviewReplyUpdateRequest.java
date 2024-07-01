package com.tenutz.storemngsim.web.api.dto.store;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreReviewReplyUpdateRequest {

    private String content;
}
