package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.menu.MainMenu;
import com.tenutz.storemngsim.domain.menu.OptionGroupMainMenu;
import com.tenutz.storemngsim.domain.menu.OptionGroupMainMenuRepository;
import com.tenutz.storemngsim.domain.menu.OptionGroupRepository;
import com.tenutz.storemngsim.web.api.dto.common.OptionGroupsDeleteRequest;
import com.tenutz.storemngsim.web.api.dto.menu.MainMenuMappersResponse;
import com.tenutz.storemngsim.web.api.dto.menu.MainMenuOptionGroupsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OptionGroupService {

    private final OptionGroupRepository optionGroupRepository;
    private final OptionGroupMainMenuRepository optionGroupMainMenuRepository;

    public MainMenuOptionGroupsResponse mainMenuOptionGroups(String strCd, String mainCateCd, String middleCateCd, String subCateCd, String mainMenuCd) {
        return new MainMenuOptionGroupsResponse(
                optionGroupRepository.mainMenuOptionGroups(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd).stream().map(optionGroup ->
                        new MainMenuOptionGroupsResponse.MainMenuOptionGroup(
                                optionGroup.getOptGrpCd(),
                                optionGroup.getOptGrpKorNm(),
                                optionGroup.toggleYn(),
                                optionGroup.mustSelectYn()
                        )
                ).collect(Collectors.toList())
        );
    }

    public MainMenuMappersResponse mainMenuMappers(String strCd, String mainCateCd, String middleCateCd, String subCateCd, String mainMenuCd) {
        return new MainMenuMappersResponse(optionGroupMainMenuRepository.mainMenuMappers(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd));
    }

    @Transactional
    public void deleteMainMenuMappers(String strCd, String mainCateCd, String middleCateCd, String subCateCd, String mainMenuCd, OptionGroupsDeleteRequest request) {
        List<OptionGroupMainMenu> foundMainMenuMappers = optionGroupMainMenuRepository.optionGroupMainMenus(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd, request.getOptionGroupCodes());
        foundMainMenuMappers.forEach(OptionGroupMainMenu::delete);
    }
}
