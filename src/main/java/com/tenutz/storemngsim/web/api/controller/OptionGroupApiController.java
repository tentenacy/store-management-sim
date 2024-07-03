package com.tenutz.storemngsim.web.api.controller;

import com.tenutz.storemngsim.web.api.dto.common.CommonCondition;
import com.tenutz.storemngsim.web.api.dto.common.MainMenuSearchRequest;
import com.tenutz.storemngsim.web.api.dto.common.OptionGroupsDeleteRequest;
import com.tenutz.storemngsim.web.api.dto.optiongroup.*;
import com.tenutz.storemngsim.web.api.dto.user.StoreArgs;
import com.tenutz.storemngsim.web.service.OptionGroupService;
import com.tenutz.storemngsim.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/option-groups")
@RequiredArgsConstructor
public class OptionGroupApiController {

    private final UserService userService;
    private final OptionGroupService optionGroupService;

    /**
     * 옵션그룹조회
     * @return
     */
    @GetMapping
    public OptionGroupsResponse optionGroups(@Valid CommonCondition commonCond) {
        StoreArgs storeArgs = userService.storeArgs();
        return optionGroupService.optionGroups(storeArgs.getSiteCd(), storeArgs.getStrCd(), commonCond);
    }

    /**
     * 옵션그룹상세
     * @param optionGroupCd 옵션그룹코드
     * @return
     */
    @GetMapping("/{optionGroupCd}")
    public OptionGroupResponse optionGroup(@PathVariable String optionGroupCd) {
        StoreArgs storeArgs = userService.storeArgs();
        return optionGroupService.optionGroup(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionGroupCd);
    }

    /**
     * 옵션그룹추가
     * @param request
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOptionGroup(@Valid @RequestBody OptionGroupCreateRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        optionGroupService.create(storeArgs.getSiteCd(), storeArgs.getStrCd(), request);
    }

    /**
     * 옵션그룹수정
     * @param optionGroupCd 옵션그룹코드
     * @param request
     */
    @PutMapping("/{optionGroupCd}")
    public void updateOptionGroup(@PathVariable String optionGroupCd, @Valid @RequestBody OptionGroupUpdateRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        optionGroupService.update(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionGroupCd, request);
    }

    /**
     * 옵션그룹삭제
     * @param optionGroupCd 옵션그룹코드
     */
    @DeleteMapping("/{optionGroupCd}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOptionGroup(@PathVariable String optionGroupCd) {
        StoreArgs storeArgs = userService.storeArgs();
        optionGroupService.delete(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionGroupCd);
    }

    /**
     * 옵션그룹복수삭제
     * @param request
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOptionGroups(@Valid @RequestBody OptionGroupsDeleteRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        optionGroupService.deleteOptionGroups(storeArgs.getSiteCd(), storeArgs.getStrCd(), request);
    }

    /**
     * 옵션그룹옵션조회
     * @param optionGroupCd 옵션그룹코드
     * @return
     */
    @GetMapping("/{optionGroupCd}/options")
    public OptionGroupOptionsResponse optionGroupOptions(@PathVariable String optionGroupCd, @Valid CommonCondition commonCond) {
        StoreArgs storeArgs = userService.storeArgs();
        return optionGroupService.optionGroupOptions(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionGroupCd, commonCond);
    }

    /**
     * 선택된옵션그룹옵션조회
     * @param optionGroupCd 옵션그룹코드
     * @return
     */
    @GetMapping("/{optionGroupCd}/option-mappers")
    public OptionGroupOptionMappersResponse optionGroupOptionMappers(@PathVariable String optionGroupCd) {
        StoreArgs storeArgs = userService.storeArgs();
        return optionGroupService.optionGroupOptionMappers(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionGroupCd);
    }

    /**
     * 옵션그룹옵션맵핑추가
     * @param optionGroupCd 옵션그룹코드
     * @param request
     */
    @PostMapping("/{optionGroupCd}/mapped-by-option")
    public void mapToOptions(@PathVariable String optionGroupCd, @Valid @RequestBody OptionsMappedByRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        optionGroupService.mapToOptions(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionGroupCd, request);
    }

    /**
     * 옵션그룹옵션순서변경
     * @param optionGroupCd 옵션그룹코드
     * @param request
     */
    @PostMapping("/{optionGroupCd}/option-mappers/priorities")
    public void changeOptionGroupOptionMapperPriorities(
            @PathVariable String optionGroupCd,
            @Valid @RequestBody OptionGroupOptionMapperPrioritiesChangeRequest request
    ) {
        StoreArgs storeArgs = userService.storeArgs();
        optionGroupService.changeOptionGroupOptionMapperPriorities(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionGroupCd, request);
    }

    /**
     * 옵션그룹옵션맵핑복수삭제
     * @param optionGroupCd 옵션그룹코드
     * @param request
     */
    @DeleteMapping("/{optionGroupCd}/option-mappers")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOptionGroupOptionMappers(
            @PathVariable String optionGroupCd,
            @Valid @RequestBody OptionGroupOptionMappersDeleteRequest request
    ) {
        StoreArgs storeArgs = userService.storeArgs();
        optionGroupService.deleteOptionGroupOptionMappers(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionGroupCd, request);
    }

    /**
     * 옵션그룹메뉴조회
     * @param optionGroupCd 옵션그룹코드
     * @param request
     * @return
     */
    @GetMapping("/{optionGroupCd}/main-menus")
    public OptionGroupMainMenusResponse optionGroupMainMenus(
            @PathVariable String optionGroupCd,
            @Valid MainMenuSearchRequest request,
            @Valid CommonCondition commonCond
    ) {
        StoreArgs storeArgs = userService.storeArgs();
        return optionGroupService.optionGroupMainMenus(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionGroupCd, request, commonCond);
    }

    /**
     * 선택된옵션그룹메뉴조회
     * @param optionGroupCd 옵션그룹코드
     * @return
     */
    @GetMapping("/{optionGroupCd}/main-menu-mappers")
    public OptionGroupMainMenuMappersResponse optionGroupMainMenuMappers(@PathVariable String optionGroupCd) {
        StoreArgs storeArgs = userService.storeArgs();
        return optionGroupService.optionGroupMainMenuMappers(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionGroupCd);
    }

    /**
     * 옵션그룹메뉴맵핑추가
     * @param optionGroupCd 옵션그룹코드
     * @param request
     */
    @PostMapping("/{optionGroupCd}/mapped-by-main-menus")
    public void mapToMainMenus(@PathVariable String optionGroupCd, @Valid @RequestBody MainMenusMappedByRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        optionGroupService.mapToMainMenus(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionGroupCd, request);
    }

    /**
     * 옵션그룹메뉴순서변경
     * @param optionGroupCd 옵션그룹코드
     * @param request
     */
    @PostMapping("/{optionGroupCd}/main-menu-mappers/priorities")
    public void changeOptionGroupMainMenuMapperPriorities(
            @PathVariable String optionGroupCd,
            @Valid @RequestBody OptionGroupMainMenuMapperPrioritiesChangeRequest request
    ) {
        StoreArgs storeArgs = userService.storeArgs();
        optionGroupService.changeOptionGroupMainMenuMapperPriorities(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionGroupCd, request);
    }

    /**
     * 옵션그룹메뉴맵핑복수삭제
     * @param optionGroupCd 옵션그룹코드
     * @param request
     */
    @DeleteMapping("/{optionGroupCd}/main-menu-mappers")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOptionGroupMainMenuMappers(
            @PathVariable String optionGroupCd,
            @Valid @RequestBody OptionGroupMainMenuMappersDeleteRequest request
    ) {
        StoreArgs storeArgs = userService.storeArgs();
        optionGroupService.deleteOptionGroupMainMenuMappers(storeArgs.getSiteCd(), storeArgs.getStrCd(), optionGroupCd, request);
    }
}
