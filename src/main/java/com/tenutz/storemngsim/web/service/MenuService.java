package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.menu.MainMenuRepository;
import com.tenutz.storemngsim.web.api.dto.menu.MainMenusResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MainMenuRepository mainMenuRepository;

    public MainMenusResponse mainMenus(String strCd, String mainCateCd, String middleCateCd, String subCateCd) {
        return new MainMenusResponse(mainMenuRepository.mainMenus(strCd, mainCateCd, middleCateCd, subCateCd).stream().map(menu ->
                new MainMenusResponse.MainMenu(
                        menu.getStrCd(),
                        menu.getCateCd1(),
                        menu.getCateCd2(),
                        menu.getCateCd3(),
                        menu.getMenuCd(),
                        menu.getMenuNm(),
                        menu.getSellAmt(),
                        menu.getSaleAmt(),
                        menu.getSellAmt() - menu.getSaleAmt(),
                        menu.getUseYnBoolean()
                )
        ).collect(Collectors.toList()));
    }
}
