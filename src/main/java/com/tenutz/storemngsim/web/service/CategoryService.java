package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.menu.Category;
import com.tenutz.storemngsim.domain.menu.CategoryRepository;
import com.tenutz.storemngsim.domain.store.StoreMaster;
import com.tenutz.storemngsim.domain.store.StoreMasterRepository;
import com.tenutz.storemngsim.web.api.dto.category.*;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException;
import com.tenutz.storemngsim.web.exception.business.CInvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final StoreMasterRepository storeMasterRepository;
    private final CategoryRepository categoryRepository;

    public MainCategoriesResponse mainCategories(String strCd) {
        return new MainCategoriesResponse(
                categoryRepository.mainCategories(strCd).stream()
                        .map(cat -> new MainCategoriesResponse.MainCategory(
                                cat.getStrCd(),
                                cat.getCateCd1(),
                                cat.getCateName(),
                                cat.getBlCdBoolean(),
                                cat.getPriority(),
                                cat.getCreatedAt(),
                                cat.getUpdatedAt()
                        )).collect(Collectors.toList())
        );
    }

    public MiddleCategoriesResponse middleCategories(String strCd, String mainCateCd) {
        return new MiddleCategoriesResponse(
                categoryRepository.middleCategories(strCd, mainCateCd).stream()
                        .map(cat -> new MiddleCategoriesResponse.MiddleCategory(
                                cat.getStrCd(),
                                cat.getCateCd1(),
                                cat.getCateCd2(),
                                cat.getCateName(),
                                cat.getBlCdBoolean(),
                                cat.getImgName(),
                                cat.getImgUrl(),
                                cat.getPriority(),
                                cat.getCreatedAt(),
                                cat.getUpdatedAt()
                        )).collect(Collectors.toList())
        );
    }

    public SubCategoriesResponse subCategories(String strCd, String mainCateCd, String middleCateCd) {
        return new SubCategoriesResponse(
                categoryRepository.subCategories(strCd, mainCateCd, middleCateCd).stream()
                        .map(cat -> new SubCategoriesResponse.SubCategory(
                                cat.getStrCd(),
                                cat.getCateCd1(),
                                cat.getCateCd2(),
                                cat.getCateCd3(),
                                cat.getCateName(),
                                cat.getBlCdBoolean(),
                                cat.getPriority(),
                                cat.getCreatedAt(),
                                cat.getUpdatedAt()
                        )).collect(Collectors.toList())
        );
    }

    public MainCategoryResponse mainCategory(String strCd, String mainCateCd) {
        Category foundMainCategory = categoryRepository.mainCategory(strCd, mainCateCd).orElseThrow(CEntityNotFoundException.CCategoryNotFoundException::new);
        return new MainCategoryResponse(
                foundMainCategory.getStrCd(),
                foundMainCategory.getCateCd1(),
                foundMainCategory.getCateName(),
                foundMainCategory.getBlCdBoolean(),
                foundMainCategory.getPriority(),
                foundMainCategory.getCreatedBy(),
                foundMainCategory.getCreatedAt(),
                foundMainCategory.getUpdatedBy(),
                foundMainCategory.getUpdatedAt()
        );
    }

    public MiddleCategoryResponse middleCategory(String strCd, String mainCateCd, String middleCateCd) {
        Category foundMainCategory = categoryRepository.middleCategory(strCd, mainCateCd, middleCateCd).orElseThrow(CEntityNotFoundException.CCategoryNotFoundException::new);
        return new MiddleCategoryResponse(
                foundMainCategory.getStrCd(),
                foundMainCategory.getCateCd1(),
                foundMainCategory.getCateCd2(),
                foundMainCategory.getCateName(),
                foundMainCategory.getBlCdBoolean(),
                foundMainCategory.getImgName(),
                foundMainCategory.getImgUrl(),
                foundMainCategory.getPriority(),
                foundMainCategory.getBizNo(),
                foundMainCategory.getOwnerName(),
                foundMainCategory.getPhoneNo(),
                foundMainCategory.getAddr(),
                foundMainCategory.getTid(),
                foundMainCategory.getCreatedBy(),
                foundMainCategory.getCreatedAt(),
                foundMainCategory.getUpdatedBy(),
                foundMainCategory.getUpdatedAt()
        );
    }

    public SubCategoryResponse subCategory(String strCd, String mainCateCd, String middleCateCd, String subCateCd) {
        Category foundMainCategory = categoryRepository.subCategory(strCd, mainCateCd, middleCateCd, subCateCd).orElseThrow(CEntityNotFoundException.CCategoryNotFoundException::new);
        return new SubCategoryResponse(
                foundMainCategory.getStrCd(),
                foundMainCategory.getCateCd1(),
                foundMainCategory.getCateCd2(),
                foundMainCategory.getCateCd3(),
                foundMainCategory.getCateName(),
                foundMainCategory.getBlCdBoolean(),
                foundMainCategory.getPriority(),
                foundMainCategory.getCreatedBy(),
                foundMainCategory.getCreatedAt(),
                foundMainCategory.getUpdatedBy(),
                foundMainCategory.getUpdatedAt()
        );
    }

    @Transactional
    public void createMainCategory(String strCd, MainCategoryCreateRequest request) {
        StoreMaster foundStoreMaster = storeMasterRepository.findAllByStrCd(strCd).stream().findFirst().orElseThrow(CEntityNotFoundException.CStoreMasterNotFoundException::new);
        categoryRepository.mainCategory(strCd, request.getCategoryCode()).ifPresent(cat -> {
            throw new CInvalidValueException.CAlreadyCategoryCreatedException();
        });
        categoryRepository.save(
                Category.createMainCategory(
                        foundStoreMaster.getSiteCd(),
                        foundStoreMaster.getStrCd(),
                        request.getCategoryCode(),
                        request.getCategoryName(),
                        request.getUse(),
                        latestPriority(categoryRepository.latestMainCategoryPriorities(strCd)) + 1
                )
        );
    }

    @Transactional
    public void updateMainCategory(String strCd, String mainCateCd, MainCategoryUpdateRequest request) {
        Category foundMainCategory = categoryRepository.mainCategory(strCd, mainCateCd).orElseThrow(CEntityNotFoundException.CCategoryNotFoundException::new);
        foundMainCategory.updateMainCategory(
                request.getCategoryName(),
                request.getUse()
        );
    }

    @Transactional
    public void deleteMainCategory(String strCd, String mainCateCd) {
        Category foundMainCategory = categoryRepository.mainCategory(strCd, mainCateCd).orElseThrow(CEntityNotFoundException.CCategoryNotFoundException::new);
        categoryRepository.delete(foundMainCategory);
    }

    @Transactional
    public void deleteMainCategories(String strCd, MainCategoriesDeleteRequest request) {
        categoryRepository.deleteMainCategories(strCd, request.getMainCategoryCodes());
    }

    @Transactional
    public void changeMainCategoryPriorities(String strCd, MainCategoryPrioritiesChangeRequest request) {
        List<Category> foundCategories = categoryRepository.mainCategories(strCd, request.getMainCategories().stream().map(MainCategoryPrioritiesChangeRequest.MainCategory::getCategoryCode).collect(Collectors.toList()));
        foundCategories.forEach(cat -> {
            request.getMainCategories().stream().filter(reqCat -> reqCat.getCategoryCode().equals(cat.getCateCd1())).findFirst().ifPresent(reqCat -> {
                cat.updatePriority(reqCat.getPriority());
            });
        });
    }

    @Transactional
    public void createMiddleCategory(String strCd, String mainCateCd, MiddleCategoryCreateRequest request) {
        StoreMaster foundStoreMaster = storeMasterRepository.findAllByStrCd(strCd).stream().findFirst().orElseThrow(CEntityNotFoundException.CStoreMasterNotFoundException::new);
        categoryRepository.middleCategory(strCd, mainCateCd, request.getCategoryCode()).ifPresent(cat -> {
            throw new CInvalidValueException.CAlreadyCategoryCreatedException();
        });
        categoryRepository.save(
                Category.createMiddleCategory(
                        foundStoreMaster.getSiteCd(),
                        foundStoreMaster.getStrCd(),
                        mainCateCd,
                        request.getCategoryCode(),
                        request.getCategoryName(),
                        request.getUse(),
                        latestPriority(categoryRepository.latestMiddleCategoryPriorities(strCd, mainCateCd)) + 1
                )
        );
    }

    private int latestPriority(List<Integer> latestPriorities) {
        return ObjectUtils.isEmpty(latestPriorities.get(0)) ? 0 : latestPriorities.get(0);
    }
}
