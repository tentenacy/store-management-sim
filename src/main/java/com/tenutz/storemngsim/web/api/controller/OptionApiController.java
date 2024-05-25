package com.tenutz.storemngsim.web.api.controller;

import com.tenutz.storemngsim.web.api.dto.common.MenuImageArgs;
import com.tenutz.storemngsim.web.api.dto.common.OptionGroupPrioritiesChangeRequest;
import com.tenutz.storemngsim.web.api.dto.common.OptionGroupsDeleteRequest;
import com.tenutz.storemngsim.web.api.dto.common.OptionGroupsMappedByRequest;
import com.tenutz.storemngsim.web.api.dto.option.*;
import com.tenutz.storemngsim.web.service.OptionGroupService;
import com.tenutz.storemngsim.web.service.OptionService;
import com.tenutz.storemngsim.web.service.cloud.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/stores/{strCd}/options")
@RequiredArgsConstructor
public class OptionApiController {

    private final OptionService optionService;
    private final OptionGroupService optionGroupService;
    private final FileUploadService fileUploadService;

    /**
     * 옵션조회
     * @param strCd 가맹점코드
     * @return
     */
    @GetMapping
    public OptionsResponse options(@PathVariable String strCd) {
        return optionService.options(strCd);
    }

    /**
     * 옵션상세
     * @param strCd 가맹점코드
     * @param optionCd 옵션코드
     * @return
     */
    @GetMapping("/{optionCd}")
    public OptionResponse option(@PathVariable String strCd, @PathVariable String optionCd) {
        return optionService.option(strCd, optionCd);
    }

    /**
     * 옵션추가
     * @param strCd 가맹점코드
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOption(@PathVariable String strCd, @Valid OptionCreateRequest request) {

        MenuImageArgs args = new MenuImageArgs(request.getImage(), strCd);

        if(!ObjectUtils.isEmpty(request.getImage())) {
            String imageUrl = fileUploadService.uploadKioskMenuImage(args);
            request.setImageName(imageUrl.substring(imageUrl.lastIndexOf("/") + 1));
            request.setImageUrl(imageUrl.substring(imageUrl.indexOf("FILE_MANAGER")));
        }

        try {
            optionService.createOption(strCd, request);
        } catch (Exception e) {
            if(!ObjectUtils.isEmpty(request.getImage())) {
                fileUploadService.deleteKioskMenuImage(request.getImageUrl(), args);
            }
            throw e;
        }
    }

    /**
     * 옵션수정
     * @param strCd 가맹점코드
     * @param optionCd 옵션코드
     * @param request
     */
    @PutMapping("/{optionCd}")
    public void updateOption(@PathVariable String strCd, @PathVariable String optionCd, @Valid OptionUpdateRequest request) {
        MenuImageArgs args = new MenuImageArgs(request.getImage(), strCd);

        if(!ObjectUtils.isEmpty(request.getImage())) {
            String imageUrl = fileUploadService.uploadKioskMenuImage(args);
            request.setImageName(imageUrl.substring(imageUrl.lastIndexOf("/") + 1));
            request.setImageUrl(imageUrl.substring(imageUrl.indexOf("FILE_MANAGER")));
        }

        try {
            optionService.updateOption(strCd, optionCd, request);
        } catch (Exception e) {
            if(!ObjectUtils.isEmpty(request.getImage())) {
                fileUploadService.deleteKioskMenuImage(request.getImageUrl(), args);
            }
            throw e;
        }
    }

    /**
     * 옵션삭제
     * @param strCd 가맹점코드
     * @param optionCd 옵션코드
     */
    @DeleteMapping("/{optionCd}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOption(@PathVariable String strCd, @PathVariable String optionCd) {
        optionService.deleteOption(strCd, optionCd);
    }

    /**
     * 옵션복수삭제
     * @param strCd 가맹점코드
     * @param request
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOptions(@PathVariable String strCd, @Valid @RequestBody OptionsDeleteRequest request) {
        optionService.deleteOptions(strCd, request);
    }

    /**
     * 옵션옵션그룹조회
     * @param strCd    가맹점코드
     * @param optionCd 옵션코드
     * @return
     */
    @GetMapping("/{optionCd}/option-groups")
    public OptionOptionGroupsResponse optionOptionGroups(@PathVariable String strCd, @PathVariable String optionCd) {
        return optionGroupService.optionOptionGroups(strCd, optionCd);
    }

    /**
     * 선택된옵션옵션그룹조회
     * @param strCd    가맹점코드
     * @param optionCd 옵션코드
     * @return
     */
    @GetMapping("/{optionCd}/mappers")
    public OptionMappersResponse optionMappers(@PathVariable String strCd, @PathVariable String optionCd) {
        return optionGroupService.optionMappers(strCd, optionCd);
    }

    /**
     * 옵션옵션그룹맵핑추가
     * @param strCd
     * @param optionCd
     * @param request
     */
    @PostMapping("/{optionCd}/mapped-by")
    @ResponseStatus(HttpStatus.CREATED)
    public void mapToOptionGroups(
            @PathVariable String strCd,
            @PathVariable String optionCd,
            @Valid @RequestBody OptionGroupsMappedByRequest request
    ) {
        optionService.mapToOptionGroups(strCd, optionCd, request);
    }

    /**
     * 옵션옵션그룹맵핑복수삭제
     * @param strCd    가맹점코드
     * @param optionCd 옵션코드
     * @param request
     */
    @DeleteMapping("/{optionCd}/mappers")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOptionMappers(
            @PathVariable String strCd,
            @PathVariable String optionCd,
            @Valid @RequestBody OptionGroupsDeleteRequest request
    ) {
        optionGroupService.deleteOptionMappers(strCd, optionCd, request);
    }

    /**
     * 옵션옵션그룹순서변경
     * @param strCd    가맹점코드
     * @param optionCd 옵션코드
     * @param request
     */
    @PostMapping("/{optionCd}/mappers/priorities")
    public void changeOptionMapperPriorities(
            @PathVariable String strCd,
            @PathVariable String optionCd,
            @Valid @RequestBody OptionGroupPrioritiesChangeRequest request
    ) {
        optionGroupService.changeOptionMapperPriorities(strCd, optionCd, request);
    }
}