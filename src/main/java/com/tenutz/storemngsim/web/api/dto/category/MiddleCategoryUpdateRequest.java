package com.tenutz.storemngsim.web.api.dto.category;

import com.tenutz.storemngsim.web.api.dto.common.ImageRequest;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MiddleCategoryUpdateRequest {

    @NotEmpty
    private String categoryName;
    @NotNull
    private Boolean use;
    private ImageRequest image;
    private String businessNumber;
    private String representativeName;
    private String tel;
    private String address;
    private String tid;
}
