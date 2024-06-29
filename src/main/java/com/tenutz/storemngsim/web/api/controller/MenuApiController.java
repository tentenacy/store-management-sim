package com.tenutz.storemngsim.web.api.controller;

import com.tenutz.storemngsim.web.api.dto.common.MenuImageArgs;
import com.tenutz.storemngsim.web.api.dto.common.OptionGroupPrioritiesChangeRequest;
import com.tenutz.storemngsim.web.api.dto.common.OptionGroupsDeleteRequest;
import com.tenutz.storemngsim.web.api.dto.common.OptionGroupsMappedByRequest;
import com.tenutz.storemngsim.web.api.dto.menu.*;
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
    public MainMenusResponse mainMenus(@PathVariable String mainCateCd, @PathVariable String middleCateCd, @PathVariable String subCateCd) {
        String strCd = userService.storeCode();
        return menuService.mainMenus(strCd, mainCateCd, middleCateCd, subCateCd);
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
        String strCd = userService.storeCode();
        return menuService.mainMenu(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd);
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
        String strCd = userService.storeCode();

        MenuImageArgs args = new MenuImageArgs(request.getImage(), strCd);

        if(!ObjectUtils.isEmpty(request.getImage())) {
            String imageUrl = fileUploadService.uploadKioskMenuImage(args);
            request.setImageName(imageUrl.substring(imageUrl.lastIndexOf("/") + 1));
            request.setImageUrl(imageUrl.substring(imageUrl.indexOf("FILE_MANAGER")));
        }

        try {
            menuService.createMainMenu(strCd, mainCateCd, middleCateCd, subCateCd, request);
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
        String strCd = userService.storeCode();
        MenuImageArgs args = new MenuImageArgs(request.getImage(), strCd);

        if(!ObjectUtils.isEmpty(request.getImage())) {
            String imageUrl = fileUploadService.uploadKioskMenuImage(args);
            request.setImageName(imageUrl.substring(imageUrl.lastIndexOf("/") + 1));
            request.setImageUrl(imageUrl.substring(imageUrl.indexOf("FILE_MANAGER")));
        }

        try {
            menuService.updateMainMenu(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd, request);
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
        String strCd = userService.storeCode();
        menuService.deleteMainMenu(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd);
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
        String strCd = userService.storeCode();
        menuService.deleteMainMenus(strCd, mainCateCd, middleCateCd, subCateCd, request);
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
        String strCd = userService.storeCode();
        menuService.changeMainMenuPriorities(strCd, mainCateCd, middleCateCd, subCateCd, request);
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
        String strCd = userService.storeCode();
        return optionGroupService.mainMenuOptionGroups(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd);
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
        String strCd = userService.storeCode();
        return optionGroupService.mainMenuMappers(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd);
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
        String strCd = userService.storeCode();
        menuService.mapToOptionGroups(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd, request);
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
        String strCd = userService.storeCode();
        optionGroupService.deleteMainMenuMappers(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd, request);
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
        String strCd = userService.storeCode();
        optionGroupService.changeMainMenuMapperPriorities(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd, request);
    }

}