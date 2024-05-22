package com.tenutz.storemngsim.web.api.controller;

import com.tenutz.storemngsim.web.api.dto.menu.MenusResponse;
import com.tenutz.storemngsim.web.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/stores/{strCd}/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}")
@RequiredArgsConstructor
public class MenuApiController {

    private final MenuService menuService;

    @GetMapping("/menus")
    public MenusResponse mainMenus(@PathVariable String strCd, @PathVariable String mainCateCd, @PathVariable String middleCateCd, @PathVariable String subCateCd) {
        return menuService.mainMenus(strCd, mainCateCd, middleCateCd, subCateCd);
    }
}
