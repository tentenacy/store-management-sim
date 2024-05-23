package com.tenutz.storemngsim.web.api.dto.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ImageResponse {

    private String imageName;
    private String imageUrl;
}
