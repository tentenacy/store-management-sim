package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.menu.*;
import com.tenutz.storemngsim.domain.store.StoreMaster;
import com.tenutz.storemngsim.domain.store.StoreMasterRepository;
import com.tenutz.storemngsim.web.api.dto.category.CategoryPrioritiesChangeRequest;
import com.tenutz.storemngsim.web.api.dto.menu.*;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException;
import com.tenutz.storemngsim.web.exception.business.CInvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MainMenuRepository mainMenuRepository;
    private final MainMenuDetailsRepository mainMenuDetailsRepository;
    private final StoreMasterRepository storeMasterRepository;

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
                mainMenuDetailsRepository.details(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd).map(MainMenuDetails::getDetails).orElse(null)
        );
    }

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public void createMainMenu(String strCd, String mainCateCd, String middleCateCd, String subCateCd, MainMenuCreateRequest request) {
        StoreMaster foundStoreMaster = storeMasterRepository.findAllByStrCd(strCd).stream().findFirst().orElseThrow(CEntityNotFoundException.CStoreMasterNotFoundException::new);
        mainMenuRepository.mainMenu(strCd, mainCateCd, middleCateCd, subCateCd, request.getMenuCode()).ifPresent(mainMenu -> {
            throw new CInvalidValueException.CAlreadyMainMenuCreatedException();
        });
        if(StringUtils.hasText(request.getIngredientDetails())) {
            mainMenuDetailsRepository.details(strCd, mainCateCd, middleCateCd, subCateCd, request.getMenuCode()).ifPresent(mainMenuDetails -> {
                throw new CInvalidValueException.CAlreadyMainMenuDetailsCreatedException();
            });
            mainMenuDetailsRepository.save(MainMenuDetails.create(
                    foundStoreMaster.getSiteCd(),
                    strCd,
                    mainCateCd,
                    middleCateCd,
                    subCateCd,
                    request.getMenuCode(),
                    request.getIngredientDetails()
            ));
        }
        mainMenuRepository.save(
                MainMenu.create(
                        foundStoreMaster.getSiteCd(),
                        strCd,
                        mainCateCd,
                        middleCateCd,
                        subCateCd,
                        request.getMenuCode(),
                        request.getMenuName(),
                        request.getPrice(),
                        !ObjectUtils.isEmpty(request.getDiscountedPrice()) ? request.getDiscountedPrice() : 0,
                        !ObjectUtils.isEmpty(request.getAdditionalPackagingPrice()) ? request.getAdditionalPackagingPrice() : 0,
                        request.getPackaging(),
                        request.getOutOfStock(),
                        request.getUse(),
                        request.getIngredientDisplay(),
                        request.getImageName(),
                        request.getMainMenuNameKor(),
                        request.getHighlightType(),
                        request.getShowDateFrom(),
                        request.getShowDateTo(),
                        request.getShowTimeFrom(),
                        request.getShowTimeTo(),
                        request.getShowDayOfWeek(),
                        request.getEventDateFrom(),
                        request.getEventDateTo(),
                        request.getEventTimeFrom(),
                        request.getEventTimeTo(),
                        request.getEventDayOfWeek(),
                        request.getMemoKor(),
                        latestPriority(mainMenuRepository.latestPriorities(strCd, mainCateCd, middleCateCd, subCateCd)) + 1
                )
        );
    }

    @Transactional
    public void updateMainMenu(String strCd, String mainCateCd, String middleCateCd, String subCateCd, String mainMenuCd, MainMenuUpdateRequest request) {
        MainMenu foundMainMenu = mainMenuRepository.mainMenu(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd).orElseThrow(CEntityNotFoundException.CMainMenuNotFoundException::new);
        foundMainMenu.update(
                request.getMenuName(),
                request.getPrice(),
                !ObjectUtils.isEmpty(request.getDiscountedPrice()) ? request.getDiscountedPrice() : 0,
                !ObjectUtils.isEmpty(request.getAdditionalPackagingPrice()) ? request.getAdditionalPackagingPrice() : 0,
                request.getPackaging(),
                request.getOutOfStock(),
                request.getUse(),
                request.getIngredientDisplay(),
                request.getImageName(),
                request.getMainMenuNameKor(),
                request.getHighlightType(),
                request.getShowDateFrom(),
                request.getShowDateTo(),
                request.getShowTimeFrom(),
                request.getShowTimeTo(),
                request.getShowDayOfWeek(),
                request.getEventDateFrom(),
                request.getEventDateTo(),
                request.getEventTimeFrom(),
                request.getEventTimeTo(),
                request.getEventDayOfWeek(),
                request.getMemoKor()
        );
    }

    @Transactional
    public void deleteMainMenu(String strCd, String mainCateCd, String middleCateCd, String subCateCd, String mainMenuCd) {
        MainMenu foundMainMenu = mainMenuRepository.mainMenu(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd).orElseThrow(CEntityNotFoundException.CMainMenuNotFoundException::new);
        foundMainMenu.delete();
    }

    @Transactional
    public void deleteMainMenus(String strCd, String mainCateCd, String middleCateCd, String subCateCd, MenusDeleteRequest request) {
        List<MainMenu> foundMenus = mainMenuRepository.mainMenus(strCd, mainCateCd, middleCateCd, subCateCd, request.getMenuCodes());
        foundMenus.forEach(MainMenu::delete);
    }

    @Transactional
    public void changeMainMenuPriorities(String strCd, String mainCateCd, String middleCateCd, String subCateCd, MenuPrioritiesChangeRequest request) {
        List<MainMenu> foundMenus = mainMenuRepository.mainMenus(strCd, mainCateCd, middleCateCd, subCateCd, request.getMenus().stream().map(MenuPrioritiesChangeRequest.MainCategory::getMenuCode).collect(Collectors.toList()));
        foundMenus.forEach(menu -> {
            request.getMenus().stream().filter(reqCat -> reqCat.getMenuCode().equals(menu.getMenuCd())).findFirst().ifPresent(reqCat -> {
                menu.updatePriority(reqCat.getPriority());
            });
        });
    }

    /*@Transactional
    public void mapToOptionGroups(String strCd, String mainCateCd, String middleCateCd, String subCateCd, MenuOptionGroupsMappedByRequest request) {

    }*/

    private int latestPriority(List<Integer> latestPriorities) {
        return latestPriorities.isEmpty() ? 0 : (ObjectUtils.isEmpty(latestPriorities.get(0)) ? 0 : latestPriorities.get(0));
    }
}
