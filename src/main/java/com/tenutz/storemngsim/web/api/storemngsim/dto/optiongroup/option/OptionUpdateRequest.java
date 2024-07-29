package com.tenutz.storemngsim.web.api.storemngsim.dto.optiongroup.option;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionUpdateRequest {

    private MultipartFile image;

    @NotEmpty
    private String optionName;
    @NotNull
    private Integer price;
    private Boolean use;

    @JsonIgnore
    private String imageName;

    @JsonIgnore
    private String imageUrl;
}
