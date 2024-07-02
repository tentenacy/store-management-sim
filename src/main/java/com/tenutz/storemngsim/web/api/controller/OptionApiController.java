package com.tenutz.storemngsim.web.api.controller;

import com.tenutz.storemngsim.web.api.dto.common.*;
import com.tenutz.storemngsim.web.api.dto.optiongroup.option.*;
import com.tenutz.storemngsim.web.api.dto.user.StoreArgs;
import com.tenutz.storemngsim.web.service.OptionGroupService;
import com.tenutz.storemngsim.web.service.OptionService;
import com.tenutz.storemngsim.web.service.UserService;
import com.tenutz.storemngsim.web.service.cloud.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/options")
@RequiredArgsConstructor
public class OptionApiController {

    private final UserService userService;
    private final OptionService optionService;
    private final OptionGroupService optionGroupService;
    private final FileUploadService fileUploadService;

    /**
     * 옵션조회
     * @return
     */
    @GetMapping
    public OptionsResponse options(@Valid CommonCondition commonCond) {
        StoreArgs storeArgs = userService.storeArgs();
        return optionService.options(storeArgs.getSiteCd(), storeArgs.getStrCd(), commonCond);
    }

    /**
     * 옵션상세
     * @param optionCd 옵션코드
     * @return
     */
    @GetMapping("/{optionCd}")
    public OptionResponse option(@PathVariable String optionCd) {
        StoreArgs storeArgs = userService.storeArgs();
        return optionService.option(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionCd);
    }

    /**
     * 옵션추가
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOption(@Valid OptionCreateRequest request) {
        StoreArgs storeArgs = userService.storeArgs();

        MenuImageArgs args = new MenuImageArgs(request.getImage(), storeArgs.getSiteCd(), storeArgs.getStrCd());

        if(!ObjectUtils.isEmpty(request.getImage())) {
            String imageUrl = fileUploadService.uploadKioskMenuImage(args);
            request.setImageName(imageUrl.substring(imageUrl.lastIndexOf("/") + 1));
            request.setImageUrl(imageUrl.substring(imageUrl.indexOf("FILE_MANAGER")));
        }

        try {
            optionService.createOption(storeArgs.getSiteCd(), storeArgs.getStrCd(), request);
        } catch (Exception e) {
            if(!ObjectUtils.isEmpty(request.getImage())) {
                fileUploadService.deleteKioskMenuImage(request.getImageUrl(), args);
            }
            throw e;
        }
    }

    /**
     * 옵션수정
     * @param optionCd 옵션코드
     * @param request
     */
    @PutMapping("/{optionCd}")
    public void updateOption(@PathVariable String optionCd, @Valid OptionUpdateRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        MenuImageArgs args = new MenuImageArgs(request.getImage(), storeArgs.getSiteCd(), storeArgs.getStrCd());

        if(!ObjectUtils.isEmpty(request.getImage())) {
            String imageUrl = fileUploadService.uploadKioskMenuImage(args);
            request.setImageName(imageUrl.substring(imageUrl.lastIndexOf("/") + 1));
            request.setImageUrl(imageUrl.substring(imageUrl.indexOf("FILE_MANAGER")));
        }

        try {
            optionService.updateOption(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionCd, request);
        } catch (Exception e) {
            if(!ObjectUtils.isEmpty(request.getImage())) {
                fileUploadService.deleteKioskMenuImage(request.getImageUrl(), args);
            }
            throw e;
        }
    }

    /**
     * 옵션삭제
     * @param optionCd 옵션코드
     */
    @DeleteMapping("/{optionCd}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOption(@PathVariable String optionCd) {
        StoreArgs storeArgs = userService.storeArgs();
        optionService.deleteOption(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionCd);
    }

    /**
     * 옵션복수삭제
     * @param request
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOptions(@Valid @RequestBody OptionsDeleteRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        optionService.deleteOptions(storeArgs.getSiteCd(), storeArgs.getStrCd(), request);
    }

    /**
     * 옵션옵션그룹조회
     * @param optionCd 옵션코드
     * @return
     */
    @GetMapping("/{optionCd}/option-groups")
    public OptionOptionGroupsResponse optionOptionGroups(@PathVariable String optionCd) {
        StoreArgs storeArgs = userService.storeArgs();
        return optionGroupService.optionOptionGroups(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionCd);
    }

    /**
     * 선택된옵션옵션그룹조회
     * @param optionCd 옵션코드
     * @return
     */
    @GetMapping("/{optionCd}/mappers")
    public OptionMappersResponse optionMappers(@PathVariable String optionCd) {
        StoreArgs storeArgs = userService.storeArgs();
        return optionGroupService.optionMappers(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionCd);
    }

    /**
     * 옵션옵션그룹맵핑추가
     * @param optionCd
     * @param request
     */
    @PostMapping("/{optionCd}/mapped-by")
    @ResponseStatus(HttpStatus.CREATED)
    public void mapToOptionGroups(
            @PathVariable String optionCd,
            @Valid @RequestBody OptionGroupsMappedByRequest request
    ) {
        StoreArgs storeArgs = userService.storeArgs();

        optionService.mapToOptionGroups(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionCd, request);
    }

    /**
     * 옵션옵션그룹맵핑복수삭제
     * @param optionCd 옵션코드
     * @param request
     */
    @DeleteMapping("/{optionCd}/mappers")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOptionMappers(
            @PathVariable String optionCd,
            @Valid @RequestBody OptionGroupsDeleteRequest request
    ) {
        StoreArgs storeArgs = userService.storeArgs();

        optionGroupService.deleteOptionMappers(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionCd, request);
    }

    /**
     * 옵션옵션그룹순서변경
     * @param optionCd 옵션코드
     * @param request
     */
    @PostMapping("/{optionCd}/mappers/priorities")
    public void changeOptionMapperPriorities(
            @PathVariable String optionCd,
            @Valid @RequestBody OptionGroupPrioritiesChangeRequest request
    ) {
        StoreArgs storeArgs = userService.storeArgs();

        optionGroupService.changeOptionMapperPriorities(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionCd, request);
    }
}