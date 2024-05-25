package com.tenutz.storemngsim.web.api.dto.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MenuImageArgs {

    private MultipartFile fileToUpload;
    private String storeCd;

    private String newFileName;

    public MenuImageArgs(MultipartFile fileToUpload, String storeCd) {
        this.fileToUpload = fileToUpload;
        this.storeCd = storeCd;
    }
}
