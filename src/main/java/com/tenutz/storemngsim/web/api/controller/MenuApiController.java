package com.tenutz.storemngsim.web.api.controller;

import com.tenutz.storemngsim.web.api.dto.menu.*;
import com.tenutz.storemngsim.web.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/stores/{strCd}/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}")
@RequiredArgsConstructor
public class MenuApiController {

    private final MenuService menuService;

    /**
     * 메뉴조회
     * @param strCd 가맹점코드
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @return
     */
    @GetMapping("/main-menus")
    public MainMenusResponse mainMenus(@PathVariable String strCd, @PathVariable String mainCateCd, @PathVariable String middleCateCd, @PathVariable String subCateCd) {
        return menuService.mainMenus(strCd, mainCateCd, middleCateCd, subCateCd);
    }

    /**
     * 메뉴상세
     * @param strCd 가맹점코드
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @param mainMenuCd 메뉴코드
     * @return
     */
    @GetMapping("/main-menus/{mainMenuCd}")
    public MainMenuResponse mainMenu(
            @PathVariable String strCd,
            @PathVariable String mainCateCd,
            @PathVariable String middleCateCd,
            @PathVariable String subCateCd,
            @PathVariable String mainMenuCd
    ) {
        return menuService.mainMenu(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd);
    }

    /**
     * 메뉴추가
     * @param strCd 가맹점코드
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @param request
     */
    @PostMapping("/main-menus")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMainMenu(
            @PathVariable String strCd,
            @PathVariable String mainCateCd,
            @PathVariable String middleCateCd,
            @PathVariable String subCateCd,
            @Valid @RequestBody MainMenuCreateRequest request
    ) {
        menuService.createMainMenu(strCd, mainCateCd, middleCateCd, subCateCd, request);
    }

    /**
     * 메뉴수정
     * @param strCd 가맹점코드
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @param mainMenuCd 메뉴코드
     * @param request
     */
    @PutMapping("/main-menus/{mainMenuCd}")
    public void updateMainMenu(
            @PathVariable String strCd,
            @PathVariable String mainCateCd,
            @PathVariable String middleCateCd,
            @PathVariable String subCateCd,
            @PathVariable String mainMenuCd,
            @Valid @RequestBody MainMenuUpdateRequest request
    ) {
        menuService.updateMainMenu(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd, request);
    }

    /**
     * 메뉴삭제
     * @param strCd 가맹점코드
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @param mainMenuCd 메뉴코드
     */
    @DeleteMapping("/main-menus/{mainMenuCd}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMainMenu(
            @PathVariable String strCd,
            @PathVariable String mainCateCd,
            @PathVariable String middleCateCd,
            @PathVariable String subCateCd,
            @PathVariable String mainMenuCd
    ) {
        menuService.deleteMainMenu(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd);
    }

    /**
     * 메뉴복수삭제
     * @param strCd 가맹점코드
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @param request
     */
    @DeleteMapping("/main-menus")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMainMenus(
            @PathVariable String strCd,
            @PathVariable String mainCateCd,
            @PathVariable String middleCateCd,
            @PathVariable String subCateCd,
            @Valid @RequestBody MenusDeleteRequest request
    ) {
        menuService.deleteMainMenus(strCd, mainCateCd, middleCateCd, subCateCd, request);
    }

    /**
     * 메뉴순서변경
     * @param strCd 가맹점코드
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @param request
     */
    @PostMapping("/main-menus/priorities")
    public void changeMainMenuPriorities(
            @PathVariable String strCd,
            @PathVariable String mainCateCd,
            @PathVariable String middleCateCd,
            @PathVariable String subCateCd,
            @Valid @RequestBody MenuPrioritiesChangeRequest request
    ) {
        menuService.changeMainMenuPriorities(strCd, mainCateCd, middleCateCd, subCateCd, request);
    }
}
