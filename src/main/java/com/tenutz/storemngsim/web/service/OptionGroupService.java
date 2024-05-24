package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.menu.*;
import com.tenutz.storemngsim.web.api.dto.common.OptionGroupPrioritiesChangeRequest;
import com.tenutz.storemngsim.web.api.dto.common.OptionGroupsDeleteRequest;
import com.tenutz.storemngsim.web.api.dto.menu.MainMenuMappersResponse;
import com.tenutz.storemngsim.web.api.dto.menu.MainMenuOptionGroupsResponse;
import com.tenutz.storemngsim.web.api.dto.option.OptionMappersResponse;
import com.tenutz.storemngsim.web.api.dto.option.OptionOptionGroupsResponse;
import com.tenutz.storemngsim.web.exception.business.CInvalidValueException;
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
    private final OptionGroupOptionRepository optionGroupOptionRepository;

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
        List<OptionGroupMainMenu> foundMainMenuMappers = optionGroupMainMenuRepository.optionGroupMainMenus(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd, request.getOptionGroupCodes(), "X");
        if(request.getOptionGroupCodes().size() != foundMainMenuMappers.size()) {
            throw new CInvalidValueException.CNonExistentOptionGroupMainMenuIncludedException();
        }
        foundMainMenuMappers.forEach(OptionGroupMainMenu::delete);
    }

    @Transactional
    public void changeMainMenuMapperPriorities(String strCd, String mainCateCd, String middleCateCd, String subCateCd, String mainMenuCd, OptionGroupPrioritiesChangeRequest request) {
        List<OptionGroupMainMenu> foundMappers = optionGroupMainMenuRepository.optionGroupMainMenus(strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd, request.getOptionGroups().stream().map(OptionGroupPrioritiesChangeRequest.OptionGroup::getOptionGroupCode).collect(Collectors.toList()), "D");
        if(request.getOptionGroups().size() != foundMappers.size()) {
            throw new CInvalidValueException.CNonExistentOptionGroupMainMenuIncludedException();
        }
        foundMappers.forEach(optionGroupMainMenu -> {
            request.getOptionGroups().stream().filter(reqOptionGroupMainMenu -> reqOptionGroupMainMenu.getOptionGroupCode().equals(optionGroupMainMenu.getOptGrpCd())).findAny().ifPresent(reqOptionGroupMainMenu -> {
                optionGroupMainMenu.updatePriority(reqOptionGroupMainMenu.getPriority());
            });
        });
    }

    public OptionOptionGroupsResponse optionOptionGroups(String strCd, String optionCd) {
        return new OptionOptionGroupsResponse(
                optionGroupRepository.optionOptionGroups(strCd, optionCd).stream().map(optionGroup ->
                        new OptionOptionGroupsResponse.OptionOptionGroup(
                                optionGroup.getOptGrpCd(),
                                optionGroup.getOptGrpKorNm(),
                                optionGroup.toggleYn(),
                                optionGroup.mustSelectYn()
                        )
                ).collect(Collectors.toList())
        );
    }

    public OptionMappersResponse optionMappers(String strCd, String optionCd) {
        return new OptionMappersResponse(optionGroupMainMenuRepository.optionMappers(strCd, optionCd));
    }

    @Transactional
    public void deleteOptionMappers(String strCd, String optionCd, OptionGroupsDeleteRequest request) {
        List<OptionGroupOption> foundOptionMappers = optionGroupOptionRepository.optionGroupOptions(strCd, optionCd, request.getOptionGroupCodes(), "X");
        if(request.getOptionGroupCodes().size() != foundOptionMappers.size()) {
            throw new CInvalidValueException.CNonExistentOptionGroupOptionIncludedException();
        }
        foundOptionMappers.forEach(OptionGroupOption::delete);
    }

    @Transactional
    public void changeOptionMapperPriorities(String strCd, String optionCd, OptionGroupPrioritiesChangeRequest request) {
        List<OptionGroupOption> foundMappers = optionGroupMainMenuRepository.optionGroupOptions(strCd, optionCd, request.getOptionGroups().stream().map(OptionGroupPrioritiesChangeRequest.OptionGroup::getOptionGroupCode).collect(Collectors.toList()), "D");
        if(request.getOptionGroups().size() != foundMappers.size()) {
            throw new CInvalidValueException.CNonExistentOptionGroupOptionIncludedException();
        }
        foundMappers.forEach(optionGroupOption -> {
            request.getOptionGroups().stream().filter(reqOptionGroupOption -> reqOptionGroupOption.getOptionGroupCode().equals(optionGroupOption.getOptGrpCd())).findAny().ifPresent(reqOptionGroupOption -> {
                optionGroupOption.updatePriority(reqOptionGroupOption.getPriority());
            });
        });
    }
}
