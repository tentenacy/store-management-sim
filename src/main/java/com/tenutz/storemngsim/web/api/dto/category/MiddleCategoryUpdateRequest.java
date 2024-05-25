package com.tenutz.storemngsim.web.api.dto.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tenutz.storemngsim.web.api.dto.common.ImageRequest;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MiddleCategoryUpdateRequest {

    private MultipartFile image;

    @NotEmpty
    private String categoryName;
    @NotNull
    private Boolean use;
    private String businessNumber;
    private String representativeName;
    private String tel;
    private String address;
    private String tid;

    @JsonIgnore
    private String imageName;

    @JsonIgnore
    private String imageUrl;
}
