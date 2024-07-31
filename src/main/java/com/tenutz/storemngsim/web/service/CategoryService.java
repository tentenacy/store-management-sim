package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.customer.StoreReviewRepository;
import com.tenutz.storemngsim.domain.menu.Category;
import com.tenutz.storemngsim.domain.menu.CategoryRepository;
import com.tenutz.storemngsim.domain.store.StoreMasterRepository;
import com.tenutz.storemngsim.web.api.common.dto.CommonCondition;
import com.tenutz.storemngsim.web.api.storemngsim.dto.category.*;
import com.tenutz.storemngsim.web.client.common.client.UploadClient;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException;
import com.tenutz.storemngsim.web.exception.business.CInvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final StoreMasterRepository storeMasterRepository;
    private final CategoryRepository categoryRepository;
    private final UploadClient s3Client;
    private final StoreReviewRepository storeReviewRepository;

    public MainCategoriesResponse mainCategories(String siteCd, String strCd, CommonCondition commonCond) {
        return new MainCategoriesResponse(
                categoryRepository.mainCategories(siteCd, strCd, commonCond).stream()
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

    public MiddleCategoriesResponse middleCategories(String siteCd, String strCd, String mainCateCd, CommonCondition commonCond) {
        return new MiddleCategoriesResponse(
                categoryRepository.middleCategories(siteCd, strCd, mainCateCd, commonCond).stream()
                        .map(cat -> new MiddleCategoriesResponse.MiddleCategory(
                                cat.getStrCd(),
                                cat.getCateCd1(),
                                cat.getCateCd2(),
                                cat.getCateName(),
                                cat.getBlCdBoolean(),
                                cat.getImgName(),
                                StringUtils.hasText(cat.getImgUrl()) ? s3Client.getFileUrl(cat.getImgUrl()) : null,
                                cat.getPriority(),
                                cat.getPhoneNo(),
                                cat.getAddr(),
                                cat.getCreatedAt(),
                                cat.getUpdatedAt()
                        )).collect(Collectors.toList())
        );
    }

    public SubCategoriesResponse subCategories(String siteCd, String strCd, String mainCateCd, String middleCateCd, CommonCondition commonCond) {
        return new SubCategoriesResponse(
                categoryRepository.subCategories(siteCd, strCd, mainCateCd, middleCateCd, commonCond).stream()
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

    public MainCategoryResponse mainCategory(String siteCd, String strCd, String mainCateCd) {
        Category foundMainCategory = categoryRepository.mainCategory(siteCd, strCd, mainCateCd).orElseThrow(CEntityNotFoundException.CCategoryNotFoundException::new);
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

    public MiddleCategoryResponse middleCategory(String siteCd, String strCd, String mainCateCd, String middleCateCd) {
        Category foundMainCategory = categoryRepository.middleCategory(siteCd, strCd, mainCateCd, middleCateCd).orElseThrow(CEntityNotFoundException.CCategoryNotFoundException::new);
        return new MiddleCategoryResponse(
                foundMainCategory.getStrCd(),
                foundMainCategory.getCateCd1(),
                foundMainCategory.getCateCd2(),
                foundMainCategory.getCateName(),
                foundMainCategory.getBlCdBoolean(),
                foundMainCategory.getImgName(),
                StringUtils.hasText(foundMainCategory.getImgUrl()) ? s3Client.getFileUrl(foundMainCategory.getImgUrl()) : null,
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

    public SubCategoryResponse subCategory(String siteCd, String strCd, String mainCateCd, String middleCateCd, String subCateCd) {
        Category foundMainCategory = categoryRepository.subCategory(siteCd, strCd, mainCateCd, middleCateCd, subCateCd).orElseThrow(CEntityNotFoundException.CCategoryNotFoundException::new);
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
    public void createMainCategory(String siteCd, String strCd, MainCategoryCreateRequest request) {
        categoryRepository.mainCategory(siteCd, strCd, request.getCategoryCode()).ifPresent(cat -> {
            throw new CInvalidValueException.CAlreadyCategoryCreatedException();
        });
        categoryRepository.save(
                Category.createMainCategory(
                        siteCd,
                        strCd,
                        request.getCategoryCode(),
                        request.getCategoryName(),
                        request.getUse(),
                        latestPriority(categoryRepository.latestMainCategoryPriorities(siteCd, strCd)) + 1
                )
        );
    }

    @Transactional
    public void updateMainCategory(String siteCd, String strCd, String mainCateCd, MainCategoryUpdateRequest request) {
        Category foundMainCategory = categoryRepository.mainCategory(siteCd, strCd, mainCateCd).orElseThrow(CEntityNotFoundException.CCategoryNotFoundException::new);
        foundMainCategory.updateMainCategory(
                request.getCategoryName(),
                request.getUse()
        );
    }

    @Transactional
    public void deleteMainCategory(String siteCd, String strCd, String mainCateCd) {
        Category foundMainCategory = categoryRepository.mainCategory(siteCd, strCd, mainCateCd).orElseThrow(CEntityNotFoundException.CCategoryNotFoundException::new);
//        categoryRepository.delete(foundMainCategory);
        foundMainCategory.doesNotUse();
    }

    @Transactional
    public void deleteMainCategories(String siteCd, String strCd, CategoriesDeleteRequest request) {
//        categoryRepository.deleteMainCategories(siteCd, strCd, request.getCategoryCodes());
        List<Category> foundCategories = categoryRepository.mainCategories(siteCd, strCd, request.getCategoryCodes());
        if(request.getCategoryCodes().size() != foundCategories.size()) {
            throw new CInvalidValueException.CNonExistentCategoryIncludedException();
        }
        foundCategories.forEach(Category::doesNotUse);
    }

    @Transactional
    public void changeMainCategoryPriorities(String siteCd, String strCd, CategoryPrioritiesChangeRequest request) {
        List<Category> foundCategories = categoryRepository.mainCategories(siteCd, strCd, request.getCategories().stream().map(CategoryPrioritiesChangeRequest.MainCategory::getCategoryCode).collect(Collectors.toList()));
        if(request.getCategories().size() != foundCategories.size()) {
            throw new CInvalidValueException.CNonExistentCategoryIncludedException();
        }
        foundCategories.forEach(cat -> {
            request.getCategories().stream().filter(reqCat -> reqCat.getCategoryCode().equals(cat.getCateCd1())).findAny().ifPresent(reqCat -> {
                cat.updatePriority(reqCat.getPriority());
            });
        });
    }

    @Transactional
    public void createMiddleCategory(String siteCd, String strCd, String mainCateCd, MiddleCategoryCreateRequest request) {
        categoryRepository.middleCategory(siteCd, strCd, mainCateCd, request.getCategoryCode()).ifPresent(cat -> {
            throw new CInvalidValueException.CAlreadyCategoryCreatedException();
        });
        categoryRepository.save(
                Category.createMiddleCategory(
                        siteCd,
                        strCd,
                        mainCateCd,
                        request.getCategoryCode(),
                        request.getCategoryName(),
                        request.getUse(),
                        latestPriority(categoryRepository.latestMiddleCategoryPriorities(siteCd, strCd, mainCateCd)) + 1,
                        request.getImageName(),
                        request.getImageUrl(),
                        request.getBusinessNumber(),
                        request.getRepresentativeName(),
                        request.getTel(),
                        request.getAddress(),
                        request.getTid()
                )
        );
    }

    @Transactional
    public void updateMiddleCategory(String siteCd, String strCd, String mainCateCd, String middleCateCd, MiddleCategoryUpdateRequest request) {
        Category foundMiddleCategory = categoryRepository.middleCategory(siteCd, strCd, mainCateCd, middleCateCd).orElseThrow(CEntityNotFoundException.CCategoryNotFoundException::new);
        String prevCateName = foundMiddleCategory.getCateName();
        foundMiddleCategory.updateMiddleCategory(
                request.getCategoryName(),
                request.getUse(),
                request.getImageName(),
                request.getImageUrl(),
                request.getBusinessNumber(),
                request.getRepresentativeName(),
                request.getTel(),
                request.getAddress(),
                request.getTid()
        );
        storeReviewRepository.storeReviews(siteCd, strCd, middleCateCd, prevCateName).forEach(review -> {
            review.updateAsMiddleCategory(foundMiddleCategory.getCateName());
            storeReviewRepository.save(review);
        });
    }

    @Transactional
    public void deleteMiddleCategory(String siteCd, String strCd, String mainCateCd, String middleCateCd) {
        Category foundMiddleCategory = categoryRepository.middleCategory(siteCd, strCd, mainCateCd, middleCateCd).orElseThrow(CEntityNotFoundException.CCategoryNotFoundException::new);
//        categoryRepository.delete(foundMiddleCategory);
        foundMiddleCategory.doesNotUse();
    }

    @Transactional
    public void deleteMiddleCategories(String siteCd, String strCd, String mainCateCd, CategoriesDeleteRequest request) {
//        categoryRepository.deleteMiddleCategories(siteCd, strCd, mainCateCd, request.getCategoryCodes());
        List<Category> foundCategories = categoryRepository.middleCategories(siteCd, strCd, mainCateCd, request.getCategoryCodes());
        if(request.getCategoryCodes().size() != foundCategories.size()) {
            throw new CInvalidValueException.CNonExistentCategoryIncludedException();
        }
        foundCategories.forEach(Category::doesNotUse);
    }

    @Transactional
    public void changeMiddleCategoryPriorities(String siteCd, String strCd, String mainCateCd, CategoryPrioritiesChangeRequest request) {
        List<Category> foundCategories = categoryRepository.middleCategories(siteCd, strCd, mainCateCd, request.getCategories().stream().map(CategoryPrioritiesChangeRequest.MainCategory::getCategoryCode).collect(Collectors.toList()));
        if(request.getCategories().size() != foundCategories.size()) {
            throw new CInvalidValueException.CNonExistentCategoryIncludedException();
        }
        foundCategories.forEach(cat -> {
            request.getCategories().stream().filter(reqCat -> reqCat.getCategoryCode().equals(cat.getCateCd2())).findAny().ifPresent(reqCat -> {
                cat.updatePriority(reqCat.getPriority());
            });
        });
    }

    @Transactional
    public void createSubCategory(String siteCd, String strCd, String mainCateCd, String middleCateCd, SubCategoryCreateRequest request) {
        categoryRepository.subCategory(siteCd, strCd, mainCateCd, middleCateCd, request.getCategoryCode()).ifPresent(cat -> {
            throw new CInvalidValueException.CAlreadyCategoryCreatedException();
        });
        categoryRepository.save(
                Category.createSubCategory(
                        siteCd,
                        strCd,
                        mainCateCd,
                        middleCateCd,
                        request.getCategoryCode(),
                        request.getCategoryName(),
                        request.getUse(),
                        latestPriority(categoryRepository.latestSubCategoryPriorities(siteCd, strCd, mainCateCd, middleCateCd)) + 1
                )
        );
    }

    @Transactional
    public void updateSubCategory(String siteCd, String strCd, String mainCateCd, String middleCateCd, String subCateCd, SubCategoryUpdateRequest request) {
        Category foundMainCategory = categoryRepository.subCategory(siteCd, strCd, mainCateCd, middleCateCd, subCateCd).orElseThrow(CEntityNotFoundException.CCategoryNotFoundException::new);
        foundMainCategory.updateSubCategory(
                request.getCategoryName(),
                request.getUse()
        );
    }

    @Transactional
    public void deleteSubCategory(String siteCd, String strCd, String mainCateCd, String middleCateCd, String subCateCd) {
        Category foundSubCategory = categoryRepository.subCategory(siteCd, strCd, mainCateCd, middleCateCd, subCateCd).orElseThrow(CEntityNotFoundException.CCategoryNotFoundException::new);
//        categoryRepository.delete(foundSubCategory);
        foundSubCategory.doesNotUse();
    }

    @Transactional
    public void deleteSubCategories(String siteCd, String strCd, String mainCateCd, String middleCateCd, CategoriesDeleteRequest request) {
//        categoryRepository.deleteSubCategories(siteCd, strCd, mainCateCd, middleCateCd, request.getCategoryCodes());
        List<Category> foundCategories = categoryRepository.subCategories(siteCd, strCd, mainCateCd, middleCateCd, request.getCategoryCodes());
        if(request.getCategoryCodes().size() != foundCategories.size()) {
            throw new CInvalidValueException.CNonExistentCategoryIncludedException();
        }
        foundCategories.forEach(Category::doesNotUse);
    }

    @Transactional
    public void changeSubCategoryPriorities(String siteCd, String strCd, String mainCateCd, String middleCateCd, CategoryPrioritiesChangeRequest request) {
        List<Category> foundCategories = categoryRepository.subCategories(siteCd, strCd, mainCateCd, middleCateCd, request.getCategories().stream().map(CategoryPrioritiesChangeRequest.MainCategory::getCategoryCode).collect(Collectors.toList()));
        if(request.getCategories().size() != foundCategories.size()) {
            throw new CInvalidValueException.CNonExistentCategoryIncludedException();
        }
        foundCategories.forEach(cat -> {
            request.getCategories().stream().filter(reqCat -> reqCat.getCategoryCode().equals(cat.getCateCd3())).findAny().ifPresent(reqCat -> {
                cat.updatePriority(reqCat.getPriority());
            });
        });
    }

    private int latestPriority(List<Integer> latestPriorities) {
        return latestPriorities.isEmpty() ? 0 : (ObjectUtils.isEmpty(latestPriorities.get(0)) ? 0 : latestPriorities.get(0));
    }
}
