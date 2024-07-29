package com.tenutz.storemngsim.web.api.storemngsim.dto.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MiddleCategoryCreateRequest {

    private MultipartFile image;

    @NotEmpty
    private String categoryCode;
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
