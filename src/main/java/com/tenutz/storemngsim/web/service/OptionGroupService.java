package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.menu.*;
import com.tenutz.storemngsim.domain.store.StoreMaster;
import com.tenutz.storemngsim.domain.store.StoreMasterRepository;
import com.tenutz.storemngsim.web.api.dto.common.MainMenuSearchRequest;
import com.tenutz.storemngsim.web.api.dto.common.OptionGroupPrioritiesChangeRequest;
import com.tenutz.storemngsim.web.api.dto.common.OptionGroupsDeleteRequest;
import com.tenutz.storemngsim.web.api.dto.menu.MainMenuMappersResponse;
import com.tenutz.storemngsim.web.api.dto.menu.MainMenuOptionGroupsResponse;
import com.tenutz.storemngsim.web.api.dto.option.OptionMappersResponse;
import com.tenutz.storemngsim.web.api.dto.option.OptionOptionGroupsResponse;
import com.tenutz.storemngsim.web.api.dto.optiongroup.*;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException;
import com.tenutz.storemngsim.web.exception.business.CInvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;

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
    private final StoreMasterRepository storeMasterRepository;
    private final OptionRepository optionRepository;
    private final MainMenuRepository mainMenuRepository;

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
        List<OptionGroupOption> foundMappers = optionGroupOptionRepository.optionGroupOptions(strCd, optionCd, request.getOptionGroups().stream().map(OptionGroupPrioritiesChangeRequest.OptionGroup::getOptionGroupCode).collect(Collectors.toList()), "D");
        if(request.getOptionGroups().size() != foundMappers.size()) {
            throw new CInvalidValueException.CNonExistentOptionGroupOptionIncludedException();
        }
        foundMappers.forEach(optionGroupOption -> {
            request.getOptionGroups().stream().filter(reqOptionGroupOption -> reqOptionGroupOption.getOptionGroupCode().equals(optionGroupOption.getOptGrpCd())).findAny().ifPresent(reqOptionGroupOption -> {
                optionGroupOption.updatePriority(reqOptionGroupOption.getPriority());
            });
        });
    }

    public OptionGroupsResponse optionGroups(String strCd) {
        return new OptionGroupsResponse(optionGroupRepository.optionGroups(strCd).stream().map(optionGroup ->
                new OptionGroupsResponse.OptionGroup(
                        optionGroup.getOptGrpCd(),
                        optionGroup.getOptGrpKorNm(),
                        optionGroup.toggleYn(),
                        optionGroup.mustSelectYn()
                )).collect(Collectors.toList()));
    }

    public OptionGroupResponse option(String strCd, String optionGroupCd) {
        OptionGroup foundOptionGroup = optionGroupRepository.optionGroup(strCd, optionGroupCd).orElseThrow(CEntityNotFoundException.COptionGroupNotFoundException::new);
        return new OptionGroupResponse(
                foundOptionGroup.getStrCd(),
                foundOptionGroup.getOptGrpCd(),
                foundOptionGroup.getOptGrpKorNm(),
                foundOptionGroup.toggleYn(),
                foundOptionGroup.mustSelectYn(),
                foundOptionGroup.useYn(),
                foundOptionGroup.getSortNum(),
                foundOptionGroup.getCreatedBy(),
                foundOptionGroup.getCreatedAt(),
                foundOptionGroup.getUpdatedBy(),
                foundOptionGroup.getUpdatedAt()
        );
    }

    @Transactional
    public void create(String strCd, OptionGroupCreateRequest request) {
        StoreMaster foundStoreMaster = storeMasterRepository.findAllByStrCd(strCd).stream().findAny().orElseThrow(CEntityNotFoundException.CStoreMasterNotFoundException::new);
        optionGroupRepository.optionGroup(strCd, request.getOptionGroupCode()).ifPresent(optionGroup -> {
            throw new CInvalidValueException.CAlreadyOptionGroupCreatedException();
        });
        optionGroupRepository.save(
                OptionGroup.create(
                        foundStoreMaster.getSiteCd(),
                        strCd,
                        request.getOptionGroupCode(),
                        request.getOptionGroupName(),
                        request.getToggleSelect(),
                        request.getRequired(),
                        latestPriority(optionGroupRepository.latestPriorities(strCd)) + 1
                )
        );
    }

    @Transactional
    public void update(String strCd, String optionGroupCd, OptionGroupUpdateRequest request) {
        OptionGroup foundOptionGroup = optionGroupRepository.optionGroup(strCd, optionGroupCd).orElseThrow(CEntityNotFoundException.COptionGroupNotFoundException::new);
        foundOptionGroup.update(
                request.getOptionGroupName(),
                request.getToggleSelect(),
                request.getRequired()
        );
    }

    @Transactional
    public void delete(String strCd, String optionGroupCd) {
        OptionGroup foundOptionGroup = optionGroupRepository.optionGroup(strCd, optionGroupCd).orElseThrow(CEntityNotFoundException.COptionGroupNotFoundException::new);
        foundOptionGroup.delete();
    }

    @Transactional
    public void deleteOptionGroups(String strCd, OptionGroupsDeleteRequest request) {
        List<OptionGroup> foundOptionGroups = optionGroupRepository.optionGroups(strCd, request.getOptionGroupCodes(), "X");
        if(request.getOptionGroupCodes().size() != foundOptionGroups.size()) {
            throw new CInvalidValueException.CNonExistentOptionGroupIncludedException();
        }
        foundOptionGroups.forEach(OptionGroup::delete);
    }

    public OptionGroupOptionsResponse optionGroupOptions(String strCd, String optionGroupCd) {
        return new OptionGroupOptionsResponse(optionRepository.optionGroupOptions(strCd, optionGroupCd).stream().map(option ->
                new OptionGroupOptionsResponse.OptionGroupOption(
                        strCd,
                        option.getOptCd(),
                        option.getOptNm(),
                        option.getSellAmt()
                )).collect(Collectors.toList())
        );
    }

    public OptionGroupOptionMappersResponse optionGroupOptionMappers(String strCd, String optionGroupCd) {
        return new OptionGroupOptionMappersResponse(optionRepository.optionGroupOptionMappers(strCd, optionGroupCd));
    }

    @Transactional
    public void mapToOptions(String strCd, String optionGroupCd, OptionsMappedByRequest request) {
        StoreMaster foundStoreMaster = storeMasterRepository.findAllByStrCd(strCd).stream().findAny().orElseThrow(CEntityNotFoundException.CStoreMasterNotFoundException::new);
        List<OptionGroupOption> foundOptionGroupOptions = optionGroupOptionRepository.optionGroupOptions2(strCd, optionGroupCd, request.getOptionCodes(), "D");
        List<String> optionGroupCodes = foundOptionGroupOptions.stream().map(OptionGroupOption::getOptGrpCd).collect(Collectors.toList());
        request.getOptionCodes().forEach(code -> {
            if(optionGroupCodes.contains(code)) {
                foundOptionGroupOptions.stream()
                        .filter(optionGroupOption -> optionGroupOption.getOptCd().equals(code))
                        .findAny().ifPresent(OptionGroupOption::use);
            } else {
                optionGroupOptionRepository.save(
                        OptionGroupOption.create(
                                foundStoreMaster.getSiteCd(),
                                strCd,
                                code,
                                optionGroupCd,
                                latestPriority(optionGroupOptionRepository.latestPriorities2(strCd, optionGroupCd)) + 1
                        )
                );
            }
        });
    }

    @Transactional
    public void changeOptionGroupOptionMapperPriorities(String strCd, String optionGroupCd, OptionGroupOptionMapperPrioritiesChangeRequest request) {
        List<OptionGroupOption> foundMappers = optionGroupOptionRepository.optionGroupOptions2(strCd, optionGroupCd, request.getOptionGroupOptions().stream().map(OptionGroupOptionMapperPrioritiesChangeRequest.OptionGroupOption::getOptionCode).collect(Collectors.toList()), "D");
        if(request.getOptionGroupOptions().size() != foundMappers.size()) {
            throw new CInvalidValueException.CNonExistentOptionGroupOptionIncludedException();
        }
        foundMappers.forEach(optionGroupOption -> {
            request.getOptionGroupOptions().stream().filter(reqOptionGroupOption -> reqOptionGroupOption.getOptionCode().equals(optionGroupOption.getOptCd())).findAny().ifPresent(reqOptionGroupOption -> {
                optionGroupOption.updatePriority(reqOptionGroupOption.getPriority());
            });
        });
    }

    @Transactional
    public void deleteOptionGroupOptionMappers(String strCd, String optionGroupCd, OptionGroupOptionMappersDeleteRequest request) {
        List<OptionGroupOption> foundOptionGroups = optionGroupOptionRepository.optionGroupOptions2(strCd, optionGroupCd, request.getOptionGroupOptionCodes(), "X");
        if(request.getOptionGroupOptionCodes().size() != foundOptionGroups.size()) {
            throw new CInvalidValueException.CNonExistentOptionGroupIncludedException();
        }
        foundOptionGroups.forEach(OptionGroupOption::delete);
    }

    public OptionGroupMainMenusResponse optionGroupMainMenus(String strCd, String optionGroupCd, MainMenuSearchRequest request) {
        return new OptionGroupMainMenusResponse(mainMenuRepository.optionGroupMainMenus(strCd, optionGroupCd, request.getMainCateCd(), request.getMiddleCateCd(), request.getSubCateCd()).stream().map(mainMenu ->
                new OptionGroupMainMenusResponse.OptionGroupMainMenu(
                        strCd,
                        mainMenu.getMenuCd(),
                        mainMenu.getMenuNm(),
                        mainMenu.getSellAmt(),
                        mainMenu.getCateCd1(),
                        mainMenu.getCateCd2(),
                        mainMenu.getCateCd3()
                )).collect(Collectors.toList())
        );
    }

    public OptionGroupMainMenuMappersResponse optionGroupMainMenuMappers(String strCd, String optionGroupCd) {
        return new OptionGroupMainMenuMappersResponse(mainMenuRepository.optionGroupMainMenuMappers(strCd, optionGroupCd));
    }

    private int latestPriority(List<Integer> latestPriorities) {
        return latestPriorities.isEmpty() ? 0 : (ObjectUtils.isEmpty(latestPriorities.get(0)) ? 0 : latestPriorities.get(0));
    }
}
