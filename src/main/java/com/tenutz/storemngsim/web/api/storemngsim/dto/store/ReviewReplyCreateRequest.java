package com.tenutz.storemngsim.web.api.storemngsim.dto.store;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewReplyCreateRequest {

    private String content;
}
