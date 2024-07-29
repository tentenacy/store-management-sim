package com.tenutz.storemngsim.web.api.kiosksim.controller;

import com.tenutz.storemngsim.web.api.kiosksim.dto.menu.KioskMenusResponse;
import com.tenutz.storemngsim.web.api.storemngsim.dto.user.StoreArgs;
import com.tenutz.storemngsim.web.service.MenuService;
import com.tenutz.storemngsim.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/app/kiosk/{kioskCode}/categories")
@RequiredArgsConstructor
public class KioskCategoryMenuApiController {

    private final UserService userService;
    private final MenuService menuService;

    /**
     * 키오스크 카테고리별메뉴조회
     * @param kioskCode 키오스크코드
     * @return 키오스크 카테고리별메뉴목록
     */
    @GetMapping("/menus")
    public KioskMenusResponse categoriesMenus(@PathVariable("kioskCode") String kioskCode) {
        StoreArgs storeArgs = userService.storeArgs(kioskCode);
        return menuService.categoriesMenus(storeArgs.getSiteCd(), storeArgs.getStrCd());
    }

}
