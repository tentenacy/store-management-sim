package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.customer.MenuReviewRepository;
import com.tenutz.storemngsim.domain.menu.*;
import com.tenutz.storemngsim.domain.store.StoreMaster;
import com.tenutz.storemngsim.domain.store.StoreMasterRepository;
import com.tenutz.storemngsim.web.api.dto.common.CommonCondition;
import com.tenutz.storemngsim.web.api.dto.common.OptionGroupsMappedByRequest;
import com.tenutz.storemngsim.web.api.dto.menu.*;
import com.tenutz.storemngsim.web.client.UploadClient;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MainMenuRepository mainMenuRepository;
    private final MainMenuDetailsRepository mainMenuDetailsRepository;
    private final StoreMasterRepository storeMasterRepository;
    private final OptionGroupMainMenuRepository optionGroupMainMenuRepository;
    private final MenuImageRepository menuImageRepository;
    private final UploadClient s3Client;
    private final MenuReviewRepository menuReviewRepository;

    public MainMenusResponse mainMenus(String siteCd, String strCd, String mainCateCd, String middleCateCd, String subCateCd, CommonCondition commonCond) {
        return new MainMenusResponse(mainMenuRepository.mainMenus(siteCd, strCd, mainCateCd, middleCateCd, subCateCd, commonCond).stream().map(menu ->
                new MainMenusResponse.MainMenu(
                        menu.getStrCd(),
                        menu.getCateCd1(),
                        menu.getCateCd2(),
                        menu.getCateCd3(),
                        menu.getMenuCd(),
                        menu.getMenuNm(),
                        menuImageRepository.findBySiteCdAndStrCdAndFileNm(siteCd, strCd, menu.getImgNm())
                                .map(image -> s3Client.getFileUrl(image.getFilePath().substring(image.getFilePath().indexOf("FILE_MANAGER"))) + "/" + image.getFileNm())
                                .orElse(null),
                        menu.soldOutYn(),
                        menu.getSellAmt(),
                        menu.getSaleAmt(),
                        menu.getSellAmt() - menu.getSaleAmt(),
                        menu.useYn()
                )
        ).collect(Collectors.toList()));
    }

    public MainMenuResponse mainMenu(String siteCd, String strCd, String mainCateCd, String middleCateCd, String subCateCd, String mainMenuCd) {
        MainMenu foundMainMenu = mainMenuRepository.mainMenu(siteCd, strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd).orElseThrow(CEntityNotFoundException.CMainMenuNotFoundException::new);
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
                menuImageRepository.findBySiteCdAndStrCdAndFileNm(siteCd, strCd, foundMainMenu.getImgNm())
                        .map(image -> s3Client.getFileUrl(image.getFilePath().substring(image.getFilePath().indexOf("FILE_MANAGER"))) + "/" + image.getFileNm())
                        .orElse(null),
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
                foundMainMenu.getMemo(),
                foundMainMenu.getSortNum(),
                mainMenuDetailsRepository.details(siteCd, strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd).map(MainMenuDetails::getDetails).orElse(null)
        );
    }

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public void createMainMenu(String siteCd, String strCd, String mainCateCd, String middleCateCd, String subCateCd, MainMenuCreateRequest request) {

        StoreMaster foundStoreMaster = storeMasterRepository.findAllByStrCd(strCd).stream().findAny().orElseThrow(CEntityNotFoundException.CStoreMasterNotFoundException::new);

        if(StringUtils.hasText(request.getIngredientDetails())) {
            mainMenuDetailsRepository.details(siteCd, strCd, mainCateCd, middleCateCd, subCateCd, request.getMenuCode()).orElseGet(() -> {
                mainMenuDetailsRepository.save(MainMenuDetails.create(
                        foundStoreMaster.getSiteCd(),
                        strCd,
                        mainCateCd,
                        middleCateCd,
                        subCateCd,
                        request.getMenuCode(),
                        request.getIngredientDetails()
                ));
                return null;
            });
        }

        Optional<MainMenu> mainMenuOptional = mainMenuRepository.mainMenu(siteCd, strCd, mainCateCd, middleCateCd, subCateCd, request.getMenuCode());
        if(mainMenuOptional.isPresent() && mainMenuOptional.get().getUseYn().equals("D")) {
            MainMenu foundMainMenu = mainMenuOptional.get();
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
                    request.getMemo()
            );
            menuReviewRepository.menuReviews(siteCd, strCd, mainCateCd, middleCateCd, subCateCd, foundMainMenu.getMenuCd()).forEach(review -> {
                review.updateAsMenu(foundMainMenu.getMenuNm(), foundMainMenu.getImgNm());
                menuReviewRepository.save(review);
            });
        } else if(mainMenuOptional.isPresent()) {
            throw new CInvalidValueException.CAlreadyMainMenuCreatedException();
        } else {
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
                            request.getMemo(),
                            latestPriority(mainMenuRepository.latestPriorities(siteCd, strCd, mainCateCd, middleCateCd, subCateCd)) + 1
                    )
            );
        }
    }

    @Transactional
    public void updateMainMenu(String siteCd, String strCd, String mainCateCd, String middleCateCd, String subCateCd, String mainMenuCd, MainMenuUpdateRequest request) {
        MainMenu foundMainMenu = mainMenuRepository.mainMenu(siteCd, strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd).orElseThrow(CEntityNotFoundException.CMainMenuNotFoundException::new);
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
                request.getMemo()
        );
        menuReviewRepository.menuReviews(siteCd, strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd).forEach(review -> {
            review.updateAsMenu(foundMainMenu.getMenuNm(), foundMainMenu.getImgNm());
            menuReviewRepository.save(review);
        });
    }

    @Transactional
    public void deleteMainMenu(String siteCd, String strCd, String mainCateCd, String middleCateCd, String subCateCd, String mainMenuCd) {
        MainMenu foundMainMenu = mainMenuRepository.mainMenu(siteCd, strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd).orElseThrow(CEntityNotFoundException.CMainMenuNotFoundException::new);
        foundMainMenu.delete();
    }

    @Transactional
    public void deleteMainMenus(String siteCd, String strCd, String mainCateCd, String middleCateCd, String subCateCd, MenusDeleteRequest request) {
        List<MainMenu> foundMenus = mainMenuRepository.mainMenus(siteCd, strCd, mainCateCd, middleCateCd, subCateCd, request.getMenuCodes(), "X");
        if(request.getMenuCodes().size() != foundMenus.size()) {
            throw new CInvalidValueException.CNonExistentMainMenuIncludedException();
        }
        foundMenus.forEach(MainMenu::delete);
    }

    @Transactional
    public void changeMainMenuPriorities(String siteCd, String strCd, String mainCateCd, String middleCateCd, String subCateCd, MenuPrioritiesChangeRequest request) {
        List<MainMenu> foundMenus = mainMenuRepository.mainMenus(siteCd, strCd, mainCateCd, middleCateCd, subCateCd, request.getMenus().stream().map(MenuPrioritiesChangeRequest.MainCategory::getMenuCode).collect(Collectors.toList()), "D");
        if(request.getMenus().size() != foundMenus.size()) {
            throw new CInvalidValueException.CNonExistentMainMenuIncludedException();
        }
        foundMenus.forEach(menu -> {
            request.getMenus().stream().filter(reqCat -> reqCat.getMenuCode().equals(menu.getMenuCd())).findAny().ifPresent(reqCat -> {
                menu.updatePriority(reqCat.getPriority());
            });
        });
    }

    @Transactional
    public void mapToOptionGroups(String siteCd, String strCd, String mainCateCd, String middleCateCd, String subCateCd, String mainMenuCd, OptionGroupsMappedByRequest request) {
        List<OptionGroupMainMenu> foundOptionGroupMainMenus = optionGroupMainMenuRepository.optionGroupMainMenus(siteCd, strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd, request.getOptionGroupCodes(), "D");
        List<String> optionGroupCodes = foundOptionGroupMainMenus.stream().map(OptionGroupMainMenu::getOptGrpCd).collect(Collectors.toList());
        request.getOptionGroupCodes().forEach(code -> {
            if(optionGroupCodes.contains(code)) {
                foundOptionGroupMainMenus.stream()
                        .filter(optionGroupOption -> optionGroupOption.getOptGrpCd().equals(code))
                        .findAny().ifPresent(OptionGroupMainMenu::use);
            } else {
                optionGroupMainMenuRepository.save(
                        OptionGroupMainMenu.create(
                                siteCd,
                                strCd,
                                mainCateCd,
                                middleCateCd,
                                subCateCd,
                                mainMenuCd,
                                code,
                                latestPriority(optionGroupMainMenuRepository.latestPriorities(siteCd, strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd)) + 1
                        )
                );
            }
        });
    }

    private int latestPriority(List<Integer> latestPriorities) {
        return latestPriorities.isEmpty() ? 0 : (ObjectUtils.isEmpty(latestPriorities.get(0)) ? 0 : latestPriorities.get(0));
    }
}
