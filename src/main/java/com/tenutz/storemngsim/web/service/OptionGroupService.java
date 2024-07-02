package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.menu.*;
import com.tenutz.storemngsim.domain.menu.id.PartialOptionGroupMainMenuId;
import com.tenutz.storemngsim.domain.store.StoreMasterRepository;
import com.tenutz.storemngsim.web.api.dto.common.CommonCondition;
import com.tenutz.storemngsim.web.api.dto.common.MainMenuSearchRequest;
import com.tenutz.storemngsim.web.api.dto.common.OptionGroupPrioritiesChangeRequest;
import com.tenutz.storemngsim.web.api.dto.common.OptionGroupsDeleteRequest;
import com.tenutz.storemngsim.web.api.dto.menu.MainMenuMappersResponse;
import com.tenutz.storemngsim.web.api.dto.menu.MainMenuOptionGroupsResponse;
import com.tenutz.storemngsim.web.api.dto.optiongroup.*;
import com.tenutz.storemngsim.web.api.dto.optiongroup.option.OptionMappersResponse;
import com.tenutz.storemngsim.web.api.dto.optiongroup.option.OptionOptionGroupsResponse;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException;
import com.tenutz.storemngsim.web.exception.business.CInvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
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

    public MainMenuOptionGroupsResponse mainMenuOptionGroups(String siteCd, String strCd, String mainCateCd, String middleCateCd, String subCateCd, String mainMenuCd) {
        return new MainMenuOptionGroupsResponse(
                optionGroupRepository.mainMenuOptionGroups(siteCd, strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd).stream().map(optionGroup ->
                        new MainMenuOptionGroupsResponse.MainMenuOptionGroup(
                                optionGroup.getOptGrpCd(),
                                optionGroup.getOptGrpKorNm(),
                                optionGroup.toggleYn(),
                                optionGroup.mustSelectYn()
                        )
                ).collect(Collectors.toList())
        );
    }

    public MainMenuMappersResponse mainMenuMappers(String siteCd, String strCd, String mainCateCd, String middleCateCd, String subCateCd, String mainMenuCd) {
        return new MainMenuMappersResponse(optionGroupMainMenuRepository.mainMenuMappers(siteCd, strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd));
    }

    @Transactional
    public void deleteMainMenuMappers(String siteCd, String strCd, String mainCateCd, String middleCateCd, String subCateCd, String mainMenuCd, OptionGroupsDeleteRequest request) {
        List<OptionGroupMainMenu> foundMainMenuMappers = optionGroupMainMenuRepository.optionGroupMainMenus(siteCd, strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd, request.getOptionGroupCodes(), "X");
        if(request.getOptionGroupCodes().size() != foundMainMenuMappers.size()) {
            throw new CInvalidValueException.CNonExistentOptionGroupMainMenuIncludedException();
        }
        foundMainMenuMappers.forEach(OptionGroupMainMenu::delete);
    }

    @Transactional
    public void changeMainMenuMapperPriorities(String siteCd, String strCd, String mainCateCd, String middleCateCd, String subCateCd, String mainMenuCd, OptionGroupPrioritiesChangeRequest request) {
        List<OptionGroupMainMenu> foundMappers = optionGroupMainMenuRepository.optionGroupMainMenus(siteCd, strCd, mainCateCd, middleCateCd, subCateCd, mainMenuCd, request.getOptionGroups().stream().map(OptionGroupPrioritiesChangeRequest.OptionGroup::getOptionGroupCode).collect(Collectors.toList()), "D");
        if(request.getOptionGroups().size() != foundMappers.size()) {
            throw new CInvalidValueException.CNonExistentOptionGroupMainMenuIncludedException();
        }
        foundMappers.forEach(optionGroupMainMenu -> {
            request.getOptionGroups().stream().filter(reqOptionGroupMainMenu -> reqOptionGroupMainMenu.getOptionGroupCode().equals(optionGroupMainMenu.getOptGrpCd())).findAny().ifPresent(reqOptionGroupMainMenu -> {
                optionGroupMainMenu.updatePriority(reqOptionGroupMainMenu.getPriority());
            });
        });
    }

    public OptionOptionGroupsResponse optionOptionGroups(String siteCd, String strCd, String optionCd) {
        return new OptionOptionGroupsResponse(
                optionGroupRepository.optionOptionGroups(siteCd, strCd, optionCd).stream().map(optionGroup ->
                        new OptionOptionGroupsResponse.OptionOptionGroup(
                                optionGroup.getOptGrpCd(),
                                optionGroup.getOptGrpKorNm(),
                                optionGroup.toggleYn(),
                                optionGroup.mustSelectYn()
                        )
                ).collect(Collectors.toList())
        );
    }

    public OptionMappersResponse optionMappers(String siteCd, String strCd, String optionCd) {
        return new OptionMappersResponse(optionGroupMainMenuRepository.optionMappers(siteCd, strCd, optionCd));
    }

    @Transactional
    public void deleteOptionMappers(String siteCd, String strCd, String optionCd, OptionGroupsDeleteRequest request) {
        List<OptionGroupOption> foundOptionMappers = optionGroupOptionRepository.optionGroupOptions(siteCd, strCd, optionCd, request.getOptionGroupCodes(), "X");
        if(request.getOptionGroupCodes().size() != foundOptionMappers.size()) {
            throw new CInvalidValueException.CNonExistentOptionGroupOptionIncludedException();
        }
        foundOptionMappers.forEach(OptionGroupOption::delete);
    }

    @Transactional
    public void changeOptionMapperPriorities(String siteCd, String strCd, String optionCd, OptionGroupPrioritiesChangeRequest request) {
        List<OptionGroupOption> foundMappers = optionGroupOptionRepository.optionGroupOptions(siteCd, strCd, optionCd, request.getOptionGroups().stream().map(OptionGroupPrioritiesChangeRequest.OptionGroup::getOptionGroupCode).collect(Collectors.toList()), "D");
        if(request.getOptionGroups().size() != foundMappers.size()) {
            throw new CInvalidValueException.CNonExistentOptionGroupOptionIncludedException();
        }
        foundMappers.forEach(optionGroupOption -> {
            request.getOptionGroups().stream().filter(reqOptionGroupOption -> reqOptionGroupOption.getOptionGroupCode().equals(optionGroupOption.getOptGrpCd())).findAny().ifPresent(reqOptionGroupOption -> {
                optionGroupOption.updatePriority(reqOptionGroupOption.getPriority());
            });
        });
    }

    public OptionGroupsResponse optionGroups(String siteCd, String strCd, CommonCondition commonCond) {
        return new OptionGroupsResponse(optionGroupRepository.optionGroups(siteCd, strCd, commonCond).stream().map(optionGroup ->
                new OptionGroupsResponse.OptionGroup(
                        optionGroup.getOptGrpCd(),
                        optionGroup.getOptGrpKorNm(),
                        optionGroup.toggleYn(),
                        optionGroup.mustSelectYn()
                )).collect(Collectors.toList()));
    }

    public OptionGroupResponse option(String siteCd, String strCd, String optionGroupCd) {
        OptionGroup foundOptionGroup = optionGroupRepository.optionGroup(siteCd, strCd, optionGroupCd).orElseThrow(CEntityNotFoundException.COptionGroupNotFoundException::new);
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
    public void create(String siteCd, String strCd, OptionGroupCreateRequest request) {
        Optional<OptionGroup> optionGroupOptional = optionGroupRepository.optionGroup(siteCd, strCd, request.getOptionGroupCode());
        if(optionGroupOptional.isPresent() && optionGroupOptional.get().getUseYn().equals("D")) {
            optionGroupOptional.get().update(
                    request.getOptionGroupName(),
                    request.getToggleSelect(),
                    request.getRequired()
            );
            optionGroupOptional.get().use();
        } else if(optionGroupOptional.isPresent()) {
            throw new CInvalidValueException.CAlreadyOptionGroupCreatedException();
        } else {
            optionGroupRepository.save(
                    OptionGroup.create(
                            siteCd,
                            strCd,
                            request.getOptionGroupCode(),
                            request.getOptionGroupName(),
                            request.getToggleSelect(),
                            request.getRequired(),
                            latestPriority(optionGroupRepository.latestPriorities(siteCd, strCd)) + 1
                    )
            );
        }
    }

    @Transactional
    public void update(String siteCd, String strCd, String optionGroupCd, OptionGroupUpdateRequest request) {
        OptionGroup foundOptionGroup = optionGroupRepository.optionGroup(siteCd, strCd, optionGroupCd).orElseThrow(CEntityNotFoundException.COptionGroupNotFoundException::new);
        foundOptionGroup.update(
                request.getOptionGroupName(),
                request.getToggleSelect(),
                request.getRequired()
        );
    }

    @Transactional
    public void delete(String siteCd, String strCd, String optionGroupCd) {
        OptionGroup foundOptionGroup = optionGroupRepository.optionGroup(siteCd, strCd, optionGroupCd).orElseThrow(CEntityNotFoundException.COptionGroupNotFoundException::new);
        foundOptionGroup.delete();
    }

    @Transactional
    public void deleteOptionGroups(String siteCd, String strCd, OptionGroupsDeleteRequest request) {
        List<OptionGroup> foundOptionGroups = optionGroupRepository.optionGroups(siteCd, strCd, request.getOptionGroupCodes(), "X");
        if(request.getOptionGroupCodes().size() != foundOptionGroups.size()) {
            throw new CInvalidValueException.CNonExistentOptionGroupIncludedException();
        }
        foundOptionGroups.forEach(OptionGroup::delete);
    }

    public OptionGroupOptionsResponse optionGroupOptions(String siteCd, String strCd, String optionGroupCd, CommonCondition commonCond) {
        return new OptionGroupOptionsResponse(optionRepository.optionGroupOptions(siteCd, strCd, optionGroupCd, commonCond).stream().map(option ->
                new OptionGroupOptionsResponse.OptionGroupOption(
                        strCd,
                        option.getOptCd(),
                        option.getOptNm(),
                        option.getSellAmt()
                )).collect(Collectors.toList())
        );
    }

    public OptionGroupOptionMappersResponse optionGroupOptionMappers(String siteCd, String strCd, String optionGroupCd) {
        return new OptionGroupOptionMappersResponse(optionRepository.optionGroupOptionMappers(siteCd, strCd, optionGroupCd));
    }

    @Transactional
    public void mapToOptions(String siteCd, String strCd, String optionGroupCd, OptionsMappedByRequest request) {
        List<OptionGroupOption> foundOptionGroupOptions = optionGroupOptionRepository.optionGroupOptions2(siteCd, strCd, optionGroupCd, request.getOptionCodes(), "D");
        List<String> optionCodes = foundOptionGroupOptions.stream().map(OptionGroupOption::getOptCd).collect(Collectors.toList());
        request.getOptionCodes().forEach(code -> {
            if(optionCodes.contains(code)) {
                foundOptionGroupOptions.stream()
                        .filter(optionGroupOption -> optionGroupOption.getOptCd().equals(code))
                        .findAny().ifPresent(OptionGroupOption::use);
            } else {
                optionGroupOptionRepository.save(
                        OptionGroupOption.create(
                                siteCd,
                                strCd,
                                code,
                                optionGroupCd,
                                latestPriority(optionGroupOptionRepository.latestPriorities2(siteCd, strCd, optionGroupCd)) + 1
                        )
                );
            }
        });
    }

    @Transactional
    public void changeOptionGroupOptionMapperPriorities(String siteCd, String strCd, String optionGroupCd, OptionGroupOptionMapperPrioritiesChangeRequest request) {
        List<OptionGroupOption> foundMappers = optionGroupOptionRepository.optionGroupOptions2(siteCd, strCd, optionGroupCd, request.getOptionGroupOptions().stream().map(OptionGroupOptionMapperPrioritiesChangeRequest.OptionGroupOption::getOptionCode).collect(Collectors.toList()), "D");
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
    public void deleteOptionGroupOptionMappers(String siteCd, String strCd, String optionGroupCd, OptionGroupOptionMappersDeleteRequest request) {
        List<OptionGroupOption> foundOptionGroups = optionGroupOptionRepository.optionGroupOptions2(siteCd, strCd, optionGroupCd, request.getOptionGroupOptionCodes(), "X");
        if(request.getOptionGroupOptionCodes().size() != foundOptionGroups.size()) {
            throw new CInvalidValueException.CNonExistentOptionGroupIncludedException();
        }
        foundOptionGroups.forEach(OptionGroupOption::delete);
    }

    public OptionGroupMainMenusResponse optionGroupMainMenus(String siteCd, String strCd, String optionGroupCd, MainMenuSearchRequest request, CommonCondition commonCond) {
        return new OptionGroupMainMenusResponse(mainMenuRepository.optionGroupMainMenus(siteCd, strCd, optionGroupCd, request.getMainCateCd(), request.getMiddleCateCd(), request.getSubCateCd(), commonCond).stream().map(mainMenu ->
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

    public OptionGroupMainMenuMappersResponse optionGroupMainMenuMappers(String siteCd, String strCd, String optionGroupCd) {
        return new OptionGroupMainMenuMappersResponse(mainMenuRepository.optionGroupMainMenuMappers(siteCd, strCd, optionGroupCd));
    }

    @Transactional
    public void mapToMainMenus(String siteCd, String strCd, String optionGroupCd, MainMenusMappedByRequest request) {
        List<OptionGroupMainMenu> foundOptionGroupMainMenus = optionGroupMainMenuRepository.findBySiteCdAndStrCdAndOptGrpCdAndPartialIdIn(
                siteCd,
                strCd,
                optionGroupCd,
                request.getMainMenusMappedBy().stream().map(mainMenusMappedBy ->
                        new PartialOptionGroupMainMenuId(
                                mainMenusMappedBy.getMainCategoryCode(),
                                mainMenusMappedBy.getMiddleCategoryCode(),
                                mainMenusMappedBy.getSubCategoryCode(),
                                mainMenusMappedBy.getMenuCode()
                        )
                ).collect(Collectors.toList())
        ).stream()
                .filter(ogmm -> !ogmm.getUseYn().equals("D"))
                .collect(Collectors.toList());
        List<MainMenusMappedByRequest.MainMenuMappedBy> mainMenusMappedBy = foundOptionGroupMainMenus.stream().map(ogmm -> new MainMenusMappedByRequest.MainMenuMappedBy(ogmm.getCateCd1(), ogmm.getCateCd2(), ogmm.getCateCd3(), ogmm.getMenuCd())).collect(Collectors.toList());
        request.getMainMenusMappedBy().forEach(mainMenuMappedBy -> {
            if(mainMenusMappedBy.contains(mainMenuMappedBy)) {
                foundOptionGroupMainMenus.stream()
                        .filter(ogmm -> new MainMenusMappedByRequest.MainMenuMappedBy(ogmm.getCateCd1(), ogmm.getCateCd2(), ogmm.getCateCd3(), ogmm.getMenuCd()).equals(mainMenuMappedBy))
                        .findAny().ifPresent(OptionGroupMainMenu::use);
            } else {
                optionGroupMainMenuRepository.save(
                        OptionGroupMainMenu.create(
                                siteCd,
                                strCd,
                                mainMenuMappedBy.getMainCategoryCode(),
                                mainMenuMappedBy.getMiddleCategoryCode(),
                                mainMenuMappedBy.getSubCategoryCode(),
                                mainMenuMappedBy.getMenuCode(),
                                optionGroupCd,
                                latestPriority(optionGroupMainMenuRepository.latestPriorities2(siteCd, strCd, optionGroupCd)) + 1
                        )
                );
            }
        });
    }

    @Transactional
    public void changeOptionGroupMainMenuMapperPriorities(String siteCd, String strCd, String optionGroupCd, OptionGroupMainMenuMapperPrioritiesChangeRequest request) {
        List<OptionGroupMainMenu> foundMappers = optionGroupMainMenuRepository.findBySiteCdAndStrCdAndOptGrpCdAndPartialIdIn(
                        siteCd,
                        strCd,
                        optionGroupCd,
                        request.getOptionGroupMainMenus().stream().map(mainMenusMappedBy ->
                                new PartialOptionGroupMainMenuId(
                                        mainMenusMappedBy.getMainCategoryCode(),
                                        mainMenusMappedBy.getMiddleCategoryCode(),
                                        mainMenusMappedBy.getSubCategoryCode(),
                                        mainMenusMappedBy.getMenuCode()
                                )
                        ).collect(Collectors.toList())
                ).stream()
                .filter(ogmm -> !ogmm.getUseYn().equals("D"))
                .collect(Collectors.toList());
        if(request.getOptionGroupMainMenus().size() != foundMappers.size()) {
            throw new CInvalidValueException.CNonExistentOptionGroupMainMenuIncludedException();
        }
        foundMappers.forEach(optionGroupMainMenu -> {
            request.getOptionGroupMainMenus().stream().filter(reqOptionGroupMainMenu ->
                    new PartialOptionGroupMainMenuId(
                            reqOptionGroupMainMenu.getMainCategoryCode(),
                            reqOptionGroupMainMenu.getMiddleCategoryCode(),
                            reqOptionGroupMainMenu.getSubCategoryCode(),
                            reqOptionGroupMainMenu.getMenuCode()
                    ).equals(optionGroupMainMenu.getPartialId())).findAny().ifPresent(reqOptionGroupMainMenu -> {
                        optionGroupMainMenu.updatePriority(reqOptionGroupMainMenu.getPriority());
            });
        });
    }

    @Transactional
    public void deleteOptionGroupMainMenuMappers(String siteCd, String strCd, String optionGroupCd, OptionGroupMainMenuMappersDeleteRequest request) {
        List<OptionGroupMainMenu> foundOptionGroupMainMenus = optionGroupMainMenuRepository.findBySiteCdAndStrCdAndOptGrpCdAndPartialIdIn(
                siteCd,
                strCd,
                optionGroupCd,
                request.getOptionGroupMainMenus().stream().map(mainMenusMappedBy ->
                        new PartialOptionGroupMainMenuId(
                                mainMenusMappedBy.getMainCategoryCode(),
                                mainMenusMappedBy.getMiddleCategoryCode(),
                                mainMenusMappedBy.getSubCategoryCode(),
                                mainMenusMappedBy.getMenuCode()
                        )
                ).collect(Collectors.toList())
        );
        if(request.getOptionGroupMainMenus().size() != foundOptionGroupMainMenus.size()) {
            throw new CInvalidValueException.CNonExistentOptionGroupIncludedException();
        }
        foundOptionGroupMainMenus.forEach(OptionGroupMainMenu::delete);
    }

    private int latestPriority(List<Integer> latestPriorities) {
        return latestPriorities.isEmpty() ? 0 : (ObjectUtils.isEmpty(latestPriorities.get(0)) ? 0 : latestPriorities.get(0));
    }
}
