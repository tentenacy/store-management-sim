package com.tenutz.storemngsim.web.api.common.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageRequest {

    @NotEmpty
    private String imageName;
    @NotEmpty
    private String imageUrl;
}
