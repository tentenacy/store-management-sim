package com.tenutz.storemngsim.web.api.storemngsim.controller;

import com.tenutz.storemngsim.web.api.common.dto.CommonCondition;
import com.tenutz.storemngsim.web.api.storemngsim.dto.category.*;
import com.tenutz.storemngsim.web.api.storemngsim.dto.user.StoreArgs;
import com.tenutz.storemngsim.web.service.CategoryService;
import com.tenutz.storemngsim.web.service.UserService;
import com.tenutz.storemngsim.web.service.cloud.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryApiController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final FileUploadService fileUploadService;

    /**
     * 대분류조회
     * @return
     */
    @GetMapping("/main")
    public MainCategoriesResponse mainCategories(@Valid CommonCondition commonCond) {
        StoreArgs storeArgs = userService.storeArgs();
        return categoryService.mainCategories(storeArgs.getSiteCd(), storeArgs.getStrCd(), commonCond);
    }

    /**
     * 대분류상세
     * @param mainCateCd 대분류코드
     * @return
     */
    @GetMapping("/main/{mainCateCd}")
    public MainCategoryResponse mainCategory(@PathVariable String mainCateCd) {
        StoreArgs storeArgs = userService.storeArgs();
        return categoryService.mainCategory(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd);
    }

    /**
     * 대분류추가
     * @param request
     */
    @PostMapping("/main")
    public void createMainCategory(@Valid @RequestBody MainCategoryCreateRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        categoryService.createMainCategory(storeArgs.getSiteCd(), storeArgs.getStrCd(), request);
    }

    /**
     * 대분류수정
     * @param mainCateCd 대분류코드
     * @param request
     */
    @PutMapping("/main/{mainCateCd}")
    public void updateMainCategory(@PathVariable String mainCateCd, @Valid @RequestBody MainCategoryUpdateRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        categoryService.updateMainCategory(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, request);
    }

    /**
     * 대분류삭제
     * @param mainCateCd 대분류코드
     */
    @DeleteMapping("/main/{mainCateCd}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMainCategory(@PathVariable String mainCateCd) {
        StoreArgs storeArgs = userService.storeArgs();
        categoryService.deleteMainCategory(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd);
    }

    /**
     * 대분류복수삭제
     * @param request
     */
    @DeleteMapping("/main")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMainCategories(@Valid @RequestBody CategoriesDeleteRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        categoryService.deleteMainCategories(storeArgs.getSiteCd(), storeArgs.getStrCd(), request);
    }

    /**
     * 대분류순서변경
     * @param request
     */
    @PostMapping("/main/priorities")
    public void changeMainCategoryPriorities(@Valid @RequestBody CategoryPrioritiesChangeRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        categoryService.changeMainCategoryPriorities(storeArgs.getSiteCd(), storeArgs.getStrCd(), request);
    }

    /**
     * 중분류조회
     * @param mainCateCd 대분류코드
     * @return
     */
    @GetMapping("/main/{mainCateCd}/middle")
    public MiddleCategoriesResponse middleCategories(@PathVariable String mainCateCd, @Valid CommonCondition commonCond) {
        StoreArgs storeArgs = userService.storeArgs();
        return categoryService.middleCategories(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, commonCond);
    }

    /**
     * 중분류상세
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @return
     */
    @GetMapping("/main/{mainCateCd}/middle/{middleCateCd}")
    public MiddleCategoryResponse middleCategory(@PathVariable String mainCateCd, @PathVariable String middleCateCd) {
        StoreArgs storeArgs = userService.storeArgs();
        return categoryService.middleCategory(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd);
    }

    /**
     * 중분류추가
     * @param mainCateCd 대분류코드
     */
    @PostMapping("/main/{mainCateCd}/middle")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMiddleCategory(@PathVariable String mainCateCd, @Valid MiddleCategoryCreateRequest request) {
        StoreArgs storeArgs = userService.storeArgs();

//        MenuImageArgs args = new MenuImageArgs(request.getImage(), strCd);

//        if(!ObjectUtils.isEmpty(request.getImage())) {
//            String imageUrl = fileUploadService.uploadKioskMenuImage(args);
//            request.setImageName(imageUrl.substring(imageUrl.lastIndexOf("/") + 1));
//            request.setImageUrl(imageUrl.substring(imageUrl.indexOf("FILE_MANAGER")));
//        }

//        try {
            categoryService.createMiddleCategory(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, request);
//        } catch (Exception e) {
//            if(!ObjectUtils.isEmpty(request.getImage())) {
//                fileUploadService.deleteKioskMenuImage(request.getImageUrl(), args);
//            }
//            throw e;
//        }
    }

    /**
     * 중분류수정
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param request
     */
    @PutMapping("/main/{mainCateCd}/middle/{middleCateCd}")
    public void updateMiddleCategory(@PathVariable String mainCateCd, @PathVariable String middleCateCd, @Valid MiddleCategoryUpdateRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
//        MenuImageArgs args = new MenuImageArgs(request.getImage(), strCd);

//        if(!ObjectUtils.isEmpty(request.getImage())) {
//            String imageUrl = fileUploadService.uploadKioskMenuImage(args);
//            request.setImageName(imageUrl.substring(imageUrl.lastIndexOf("/") + 1));
//            request.setImageUrl(imageUrl.substring(imageUrl.indexOf("FILE_MANAGER")));
//        }

//        try {
            categoryService.updateMiddleCategory(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, request);
//        } catch (Exception e) {
//            if(!ObjectUtils.isEmpty(request.getImage())) {
//                fileUploadService.deleteKioskMenuImage(request.getImageUrl(), args);
//            }
//            throw e;
//        }
    }

    /**
     * 중분류삭제
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     */
    @DeleteMapping("/main/{mainCateCd}/middle/{middleCateCd}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMiddleCategory(@PathVariable String mainCateCd, @PathVariable String middleCateCd) {
        StoreArgs storeArgs = userService.storeArgs();
        categoryService.deleteMiddleCategory(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd);
    }

    /**
     * 중분류복수삭제
     * @param mainCateCd 대분류코드
     * @param request
     */
    @DeleteMapping("/main/{mainCateCd}/middle")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMiddleCategories(@PathVariable String mainCateCd, @Valid @RequestBody CategoriesDeleteRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        categoryService.deleteMiddleCategories(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, request);
    }

    /**
     * 중분류순서변경
     * @param mainCateCd 대분류코드
     * @param request
     */
    @PostMapping("/main/{mainCateCd}/middle/priorities")
    public void changeMiddleCategoryPriorities(@PathVariable String mainCateCd, @Valid @RequestBody CategoryPrioritiesChangeRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        categoryService.changeMiddleCategoryPriorities(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, request);
    }

    /**
     * 소분류조회
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @return
     */
    @GetMapping("/main/{mainCateCd}/middle/{middleCateCd}/sub")
    public SubCategoriesResponse subCategories(@PathVariable String mainCateCd, @PathVariable String middleCateCd, @Valid CommonCondition commonCond) {
        StoreArgs storeArgs = userService.storeArgs();

        SubCategoriesResponse subCategoriesResponse = categoryService.subCategories(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, commonCond);
        MiddleCategoryResponse middleCategoryResponse = categoryService.middleCategory(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd);
        subCategoriesResponse.setMiddleCategory(middleCategoryResponse);

        return subCategoriesResponse;
    }

    /**
     * 소분류상세
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @return
     */
    @GetMapping("/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}")
    public SubCategoryResponse subCategory(@PathVariable String mainCateCd, @PathVariable String middleCateCd, @PathVariable String subCateCd) {
        StoreArgs storeArgs = userService.storeArgs();
        return categoryService.subCategory(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, subCateCd);
    }

    /**
     * 소분류추가
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param request
     */
    @PostMapping("/main/{mainCateCd}/middle/{middleCateCd}/sub")
    public void createSubCategory(@PathVariable String mainCateCd, @PathVariable String middleCateCd, @Valid @RequestBody SubCategoryCreateRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        categoryService.createSubCategory(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, request);
    }

    /**
     * 소분류수정
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @param request
     */
    @PutMapping("/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}")
    public void updateSubCategory(@PathVariable String mainCateCd, @PathVariable String middleCateCd, @PathVariable String subCateCd, @Valid @RequestBody SubCategoryUpdateRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        categoryService.updateSubCategory(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, subCateCd, request);
    }

    /**
     * 소분류삭제
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     */
    @DeleteMapping("/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubCategory(@PathVariable String mainCateCd, @PathVariable String middleCateCd, @PathVariable String subCateCd) {
        StoreArgs storeArgs = userService.storeArgs();
        categoryService.deleteSubCategory(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, subCateCd);
    }

    /**
     * 소분류복수삭제
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param request
     */
    @DeleteMapping("/main/{mainCateCd}/middle/{middleCateCd}/sub")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubCategories(@PathVariable String mainCateCd, @PathVariable String middleCateCd, @Valid @RequestBody CategoriesDeleteRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        categoryService.deleteSubCategories(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, request);
    }

    /**
     * 소분류순서변경
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param request
     */
    @PostMapping("/main/{mainCateCd}/middle/{middleCateCd}/sub/priorities")
    public void changeSubCategoryPriorities(@PathVariable String mainCateCd, @PathVariable String middleCateCd, @Valid @RequestBody CategoryPrioritiesChangeRequest request) {
        StoreArgs storeArgs = userService.storeArgs();
        categoryService.changeSubCategoryPriorities(storeArgs.getSiteCd(), storeArgs.getStrCd(), mainCateCd, middleCateCd, request);
    }
}