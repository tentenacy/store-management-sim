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
    private String siteCd;
    private String storeCd;
    private String mainCateCd;
    private String middleCateCd;
    private String subCateCd;
    private String mainMenuCd;
    private String optionCd;

    private String newFileName;

    public MenuImageArgs(MultipartFile fileToUpload, String siteCd, String storeCd, String mainCateCd, String middleCateCd, String subCateCd, String mainMenuCd) {
        this.fileToUpload = fileToUpload;
        this.siteCd = siteCd;
        this.storeCd = storeCd;
        this.mainCateCd = mainCateCd;
        this.middleCateCd = middleCateCd;
        this.subCateCd = subCateCd;
        this.mainMenuCd = mainMenuCd;
    }

    public MenuImageArgs(MultipartFile fileToUpload, String siteCd, String storeCd, String optionCd) {
        this.fileToUpload = fileToUpload;
        this.siteCd = siteCd;
        this.storeCd = storeCd;
        this.optionCd = optionCd;
    }
}
