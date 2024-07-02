package com.tenutz.storemngsim.web.api.controller;

import com.tenutz.storemngsim.web.api.dto.common.*;
import com.tenutz.storemngsim.web.api.dto.menu.*;
import com.tenutz.storemngsim.web.api.dto.user.StoreArgs;
import com.tenutz.storemngsim.web.service.MenuService;
import com.tenutz.storemngsim.web.service.OptionGroupService;
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
@RequestMapping("/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}")
@RequiredArgsConstructor
public class MenuApiController {

    private final UserService userService;
    private final MenuService menuService;
    private final OptionGroupService optionGroupService;
    private final FileUploadService fileUploadService;

    /**
     * 메뉴조회
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @return
     */
    @GetMapping("/main-menus")
    public MainMenusResponse mainMenus(@PathVariable String mainCateCd, @PathVariable String middleCateCd, @PathVariable String subCateCd, @Valid CommonCondition commonCond) {
        StoreArgs storeArgs = userService.storeArgs();
        return menuService.mainMenus(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, subCateCd, commonCond);
    }

    /**
     * 메뉴상세
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @param mainMenuCd 메뉴코드
     * @return
     */
    @GetMapping("/main-menus/{mainMenuCd}")
    public MainMenuResponse mainMenu(
            @PathVariable String mainCateCd,
            @PathVariable String middleCateCd,
            @PathVariable String subCateCd,
            @PathVariable String mainMenuCd
    ) {
        StoreArgs storeArgs = userService.storeArgs();
        return menuService.mainMenu(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, subCateCd, mainMenuCd);
    }

    /**
     * 메뉴추가
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @param request
     */
    @PostMapping("/main-menus")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMainMenu(
            @PathVariable String mainCateCd,
            @PathVariable String middleCateCd,
            @PathVariable String subCateCd,
            @Valid MainMenuCreateRequest request
    ) {
        StoreArgs storeArgs = userService.storeArgs();

        MenuImageArgs args = new MenuImageArgs(request.getImage(), storeArgs.getSiteCd(), storeArgs.getStrCd());

        if(!ObjectUtils.isEmpty(request.getImage())) {
            String imageUrl = fileUploadService.uploadKioskMenuImage(args);
            request.setImageName(imageUrl.substring(imageUrl.lastIndexOf("/") + 1));
            request.setImageUrl(imageUrl.substring(imageUrl.indexOf("FILE_MANAGER")));
        }

        try {
            menuService.createMainMenu(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, subCateCd, request);
        } catch (Exception e) {
            if(!ObjectUtils.isEmpty(request.getImage())) {
                fileUploadService.deleteKioskMenuImage(request.getImageUrl(), args);
            }
            throw e;
        }
    }

    /**
     * 메뉴수정
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @param mainMenuCd 메뉴코드
     * @param request
     */
    @PutMapping("/main-menus/{mainMenuCd}")
    public void updateMainMenu(
            @PathVariable String mainCateCd,
            @PathVariable String middleCateCd,
            @PathVariable String subCateCd,
            @PathVariable String mainMenuCd,
            @Valid MainMenuUpdateRequest request
    ) {
        StoreArgs storeArgs = userService.storeArgs();
        MenuImageArgs args = new MenuImageArgs(request.getImage(), storeArgs.getSiteCd(), storeArgs.getStrCd());

        if(!ObjectUtils.isEmpty(request.getImage())) {
            String imageUrl = fileUploadService.uploadKioskMenuImage(args);
            request.setImageName(imageUrl.substring(imageUrl.lastIndexOf("/") + 1));
            request.setImageUrl(imageUrl.substring(imageUrl.indexOf("FILE_MANAGER")));
        }

        try {
            menuService.updateMainMenu(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, subCateCd, mainMenuCd, request);
        } catch (Exception e) {
            if(!ObjectUtils.isEmpty(request.getImage())) {
                fileUploadService.deleteKioskMenuImage(request.getImageUrl(), args);
            }
            throw e;
        }
    }

    /**
     * 메뉴삭제
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @param mainMenuCd 메뉴코드
     */
    @DeleteMapping("/main-menus/{mainMenuCd}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMainMenu(
            @PathVariable String mainCateCd,
            @PathVariable String middleCateCd,
            @PathVariable String subCateCd,
            @PathVariable String mainMenuCd
    ) {
        StoreArgs storeArgs = userService.storeArgs();
        menuService.deleteMainMenu(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, subCateCd, mainMenuCd);
    }

    /**
     * 메뉴복수삭제
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @param request
     */
    @DeleteMapping("/main-menus")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMainMenus(
            @PathVariable String mainCateCd,
            @PathVariable String middleCateCd,
            @PathVariable String subCateCd,
            @Valid @RequestBody MenusDeleteRequest request
            ) {
        StoreArgs storeArgs = userService.storeArgs();
        menuService.deleteMainMenus(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, subCateCd, request);
    }

    /**
     * 메뉴순서변경
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @param request
     */
    @PostMapping("/main-menus/priorities")
    public void changeMainMenuPriorities(
            @PathVariable String mainCateCd,
            @PathVariable String middleCateCd,
            @PathVariable String subCateCd,
            @Valid @RequestBody MenuPrioritiesChangeRequest request
    ) {
        StoreArgs storeArgs = userService.storeArgs();
        menuService.changeMainMenuPriorities(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, subCateCd, request);
    }

    /**
     * 메뉴옵션그룹조회
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @param mainMenuCd 메뉴코드
     * @return
     */
    @GetMapping("/main-menus/{mainMenuCd}/option-groups")
    public MainMenuOptionGroupsResponse mainMenuOptionGroups(
            @PathVariable String mainCateCd,
            @PathVariable String middleCateCd,
            @PathVariable String subCateCd,
            @PathVariable String mainMenuCd
    ) {
        StoreArgs storeArgs = userService.storeArgs();
        return optionGroupService.mainMenuOptionGroups(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, subCateCd, mainMenuCd);
    }

    /**
     * 선택된메뉴옵션그룹조회
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @param mainMenuCd 메뉴코드
     * @return
     */
    @GetMapping("/main-menus/{mainMenuCd}/mappers")
    public MainMenuMappersResponse mainMenuMappers(
            @PathVariable String mainCateCd,
            @PathVariable String middleCateCd,
            @PathVariable String subCateCd,
            @PathVariable String mainMenuCd
    ) {
        StoreArgs storeArgs = userService.storeArgs();
        return optionGroupService.mainMenuMappers(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, subCateCd, mainMenuCd);
    }

    /**
     * 메뉴옵션그룹맵핑추가
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @param mainMenuCd 메뉴코드
     * @param request
     */
    @PostMapping("/main-menus/{mainMenuCd}/mapped-by")
    @ResponseStatus(HttpStatus.CREATED)
    public void mapToOptionGroups(
            @PathVariable String mainCateCd,
            @PathVariable String middleCateCd,
            @PathVariable String subCateCd,
            @PathVariable String mainMenuCd,
            @Valid @RequestBody OptionGroupsMappedByRequest request
    ) {
        StoreArgs storeArgs = userService.storeArgs();
        menuService.mapToOptionGroups(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, subCateCd, mainMenuCd, request);
    }

    /**
     * 메뉴옵션그룹맵핑복수삭제
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @param mainMenuCd 메뉴코드
     * @param request
     */
    @DeleteMapping("/main-menus/{mainMenuCd}/mappers")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMainMenuMappers(
            @PathVariable String mainCateCd,
            @PathVariable String middleCateCd,
            @PathVariable String subCateCd,
            @PathVariable String mainMenuCd,
            @Valid @RequestBody OptionGroupsDeleteRequest request
            ) {
        StoreArgs storeArgs = userService.storeArgs();
        optionGroupService.deleteMainMenuMappers(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, subCateCd, mainMenuCd, request);
    }

    /**
     * 메뉴옵션그룹맵핑순서변경
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @param mainMenuCd 메뉴코드
     * @param request
     */
    @PostMapping("/main-menus/{mainMenuCd}/mappers/priorities")
    public void changeMainMenuMapperPriorities(
            @PathVariable String mainCateCd,
            @PathVariable String middleCateCd,
            @PathVariable String subCateCd,
            @PathVariable String mainMenuCd,
            @Valid @RequestBody OptionGroupPrioritiesChangeRequest request
    ) {
        StoreArgs storeArgs = userService.storeArgs();
        optionGroupService.changeMainMenuMapperPriorities(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, subCateCd, mainMenuCd, request);
    }
    
}