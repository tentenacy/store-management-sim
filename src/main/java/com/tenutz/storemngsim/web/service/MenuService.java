package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.menu.MainMenu;
import com.tenutz.storemngsim.domain.menu.MainMenuDetails;
import com.tenutz.storemngsim.domain.menu.MainMenuDetailsRepository;
import com.tenutz.storemngsim.domain.menu.MainMenuRepository;
import com.tenutz.storemngsim.web.api.dto.menu.MainMenuResponse;
import com.tenutz.storemngsim.web.api.dto.menu.MainMenusResponse;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MainMenuRepository mainMenuRepository;
    private final MainMenuDetailsRepository mainMenuDetailsRepository;

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
                        menu.useYn()
                )
        ).collect(Collectors.toList()));
    }

    public MainMenuResponse mainMenu(String strCd, String mainCateCd, String middleCateCd, String subCateCd, String mainMenuCd) {
        MainMenu foundMainMenu = mainMenuRepository.mainMenu(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd).orElseThrow(CEntityNotFoundException.CMainMenuNotFoundException::new);
        MainMenuDetails foundMainMenuDetails = mainMenuDetailsRepository.details(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd).orElse(null);
        return new MainMenuResponse(
                foundMainMenu.getStrCd(),
                foundMainMenu.getCateCd1(),
                foundMainMenu.getCateCd2(),
                foundMainMenu.getCateCd3(),
                foundMainMenu.getMenuCd(),
                foundMainMenu.getMenuNm(),
                foundMainMenu.getSellAmt(),
                foundMainMenu.getSaleAmt(),
                foundMainMenu.getTakeoutAmt(),
                foundMainMenu.getTakeoutGbcd(),
                foundMainMenu.soldOutYn(),
                foundMainMenu.useYn(),
                foundMainMenu.showDetailYn(),
                foundMainMenu.getImgNm(),
                foundMainMenu.getMenuKorNm(),
                foundMainMenu.getHighlightType(),
                foundMainMenu.getShowSdate(),
                foundMainMenu.getShowEdate(),
                foundMainMenu.getShowStime(),
                foundMainMenu.getShowEtime(),
                foundMainMenu.getShowWeekday(),
                foundMainMenu.getEvtSdate(),
                foundMainMenu.getEvtEdate(),
                foundMainMenu.getEvtStime(),
                foundMainMenu.getEvtEtime(),
                foundMainMenu.getEvtWeekday(),
                foundMainMenu.getMemoKor(),
                foundMainMenu.getSortNum(),
                !ObjectUtils.isEmpty(foundMainMenuDetails) ? foundMainMenuDetails.getDetails() : null
        );
    }
}
