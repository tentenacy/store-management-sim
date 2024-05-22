package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.menu.Category;
import com.tenutz.storemngsim.domain.menu.CategoryRepository;
import com.tenutz.storemngsim.web.api.dto.category.*;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

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
}
