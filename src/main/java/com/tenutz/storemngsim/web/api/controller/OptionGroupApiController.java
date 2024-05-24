package com.tenutz.storemngsim.web.api.controller;

import com.tenutz.storemngsim.web.api.dto.common.MainMenuSearchRequest;
import com.tenutz.storemngsim.web.api.dto.common.OptionGroupsDeleteRequest;
import com.tenutz.storemngsim.web.api.dto.optiongroup.*;
import com.tenutz.storemngsim.web.service.OptionGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/stores/{strCd}/option-groups")
@RequiredArgsConstructor
public class OptionGroupApiController {

    private final OptionGroupService optionGroupService;

    /**
     * 옵션그룹조회
     * @param strCd 가맹점코드
     * @return
     */
    @GetMapping
    public OptionGroupsResponse optionGroups(@PathVariable String strCd) {
        return optionGroupService.optionGroups(strCd);
    }

    /**
     * 옵션그룹상세
     * @param strCd         가맹점코드
     * @param optionGroupCd 옵션그룹코드
     * @return
     */
    @GetMapping("/{optionGroupCd}")
    public OptionGroupResponse optionGroup(@PathVariable String strCd, @PathVariable String optionGroupCd) {
        return optionGroupService.option(strCd, optionGroupCd);
    }

    /**
     * 옵션그룹추가
     * @param strCd   가맹점코드
     * @param request
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOptionGroup(@PathVariable String strCd, @Valid @RequestBody OptionGroupCreateRequest request) {
        optionGroupService.create(strCd, request);
    }

    /**
     * 옵션그룹수정
     * @param strCd         가맹점코드
     * @param optionGroupCd 옵션그룹코드
     * @param request
     */
    @PutMapping("/{optionGroupCd}")
    public void updateOptionGroup(@PathVariable String strCd, @PathVariable String optionGroupCd, @Valid @RequestBody OptionGroupUpdateRequest request) {
        optionGroupService.update(strCd, optionGroupCd, request);
    }

    /**
     * 옵션그룹삭제
     * @param strCd         가맹점코드
     * @param optionGroupCd 옵션그룹코드
     */
    @DeleteMapping("/{optionGroupCd}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOptionGroup(@PathVariable String strCd, @PathVariable String optionGroupCd) {
        optionGroupService.delete(strCd, optionGroupCd);
    }

    /**
     * 옵션그룹복수삭제
     * @param strCd   가맹점코드
     * @param request
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOptionGroups(@PathVariable String strCd, @Valid @RequestBody OptionGroupsDeleteRequest request) {
        optionGroupService.deleteOptionGroups(strCd, request);
    }

    /**
     * 옵션그룹옵션조회
     * @param strCd         가맹점코드
     * @param optionGroupCd 옵션그룹코드
     * @return
     */
    @GetMapping("/{optionGroupCd}/options")
    public OptionGroupOptionsResponse optionGroupOptions(@PathVariable String strCd, @PathVariable String optionGroupCd) {
        return optionGroupService.optionGroupOptions(strCd, optionGroupCd);
    }

    /**
     * 선택된옵션그룹옵션조회
     * @param strCd         가맹점코드
     * @param optionGroupCd 옵션그룹코드
     * @return
     */
    @GetMapping("/{optionGroupCd}/option-mappers")
    public OptionGroupOptionMappersResponse optionGroupOptionMappers(@PathVariable String strCd, @PathVariable String optionGroupCd) {
        return optionGroupService.optionGroupOptionMappers(strCd, optionGroupCd);
    }

    /**
     * 옵션그룹옵션맵핑추가
     * @param strCd         가맹점코드
     * @param optionGroupCd 옵션그룹코드
     * @param request
     */
    @PostMapping("/{optionGroupCd}/mapped-by-option")
    public void mapToOptions(@PathVariable String strCd, @PathVariable String optionGroupCd, @Valid @RequestBody OptionsMappedByRequest request) {
        optionGroupService.mapToOptions(strCd, optionGroupCd, request);
    }

    /**
     * 옵션그룹옵션순서변경
     * @param strCd         가맹점코드
     * @param optionGroupCd 옵션그룹코드
     * @param request
     */
    @PostMapping("/{optionGroupCd}/option-mappers/priorities")
    public void changeOptionGroupOptionMapperPriorities(
            @PathVariable String strCd,
            @PathVariable String optionGroupCd,
            @Valid @RequestBody OptionGroupOptionMapperPrioritiesChangeRequest request
    ) {
        optionGroupService.changeOptionGroupOptionMapperPriorities(strCd, optionGroupCd, request);
    }

    /**
     * 옵션그룹옵션맵핑복수삭제
     * @param strCd         가맹점코드
     * @param optionGroupCd 옵션그룹코드
     * @param request
     */
    @DeleteMapping("/{optionGroupCd}/option-mappers")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOptionGroupOptionMappers(
            @PathVariable String strCd,
            @PathVariable String optionGroupCd,
            @Valid @RequestBody OptionGroupOptionMappersDeleteRequest request
    ) {
        optionGroupService.deleteOptionGroupOptionMappers(strCd, optionGroupCd, request);
    }

    /**
     * 옵션그룹메뉴조회
     * @param strCd         가맹점코드
     * @param optionGroupCd 옵션그룹코드
     * @param request
     * @return
     */
    @GetMapping("/{optionGroupCd}/main-menus")
    public OptionGroupMainMenusResponse optionGroupMainMenus(
            @PathVariable String strCd,
            @PathVariable String optionGroupCd,
            @Valid MainMenuSearchRequest request
    ) {
        return optionGroupService.optionGroupMainMenus(strCd, optionGroupCd, request);
    }

    /**
     * 선택된옵션그룹메뉴조회
     * @param strCd         가맹점코드
     * @param optionGroupCd 옵션그룹코드
     * @return
     */
    @GetMapping("/{optionGroupCd}/main-menu-mappers")
    public OptionGroupMainMenuMappersResponse optionGroupMainMenuMappers(@PathVariable String strCd, @PathVariable String optionGroupCd) {
        return optionGroupService.optionGroupMainMenuMappers(strCd, optionGroupCd);
    }

    /**
     * 옵션그룹메뉴맵핑추가
     * @param strCd         가맹점코드
     * @param optionGroupCd 옵션그룹코드
     * @param request
     */
    @PostMapping("/{optionGroupCd}/mapped-by-main-menus")
    public void mapToMainMenus(@PathVariable String strCd, @PathVariable String optionGroupCd, @Valid @RequestBody com.tenutz.storemngsim.web.api.dto.optiongroup.MainMenusMappedByRequest request) {
        optionGroupService.mapToMainMenus(strCd, optionGroupCd, request);
    }

    /**
     * 옵션그룹메뉴순서변경
     * @param strCd         가맹점코드
     * @param optionGroupCd 옵션그룹코드
     * @param request
     */
    @PostMapping("/{optionGroupCd}/main-menu-mappers/priorities")
    public void changeOptionGroupMainMenuMapperPriorities(
            @PathVariable String strCd,
            @PathVariable String optionGroupCd,
            @Valid @RequestBody OptionGroupMainMenuMapperPrioritiesChangeRequest request
    ) {
        optionGroupService.changeOptionGroupMainMenuMapperPriorities(strCd, optionGroupCd, request);
    }

    /**
     * 옵션그룹메뉴맵핑복수삭제
     * @param strCd         가맹점코드
     * @param optionGroupCd 옵션그룹코드
     * @param request
     */
    @DeleteMapping("/{optionGroupCd}/main-menu-mappers")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOptionGroupMainMenuMappers(
            @PathVariable String strCd,
            @PathVariable String optionGroupCd,
            @Valid @RequestBody com.tenutz.storemngsim.web.api.dto.optiongroup.OptionGroupMainMenuMappersDeleteRequest request
    ) {
        optionGroupService.deleteOptionGroupMainMenuMappers(strCd, optionGroupCd, request);
    }
}
