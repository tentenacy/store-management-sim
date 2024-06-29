package com.tenutz.storemngsim.web.api.controller;

import com.tenutz.storemngsim.web.api.dto.category.*;
import com.tenutz.storemngsim.web.api.dto.common.MenuImageArgs;
import com.tenutz.storemngsim.web.service.CategoryService;
import com.tenutz.storemngsim.web.service.UserService;
import com.tenutz.storemngsim.web.service.cloud.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
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
    public MainCategoriesResponse mainCategories() {
        String strCd = userService.storeCode();
        return categoryService.mainCategories(strCd);
    }

    /**
     * 대분류상세
     * @param mainCateCd 대분류코드
     * @return
     */
    @GetMapping("/main/{mainCateCd}")
    public MainCategoryResponse mainCategory(@PathVariable String mainCateCd) {
        String strCd = userService.storeCode();
        return categoryService.mainCategory(strCd, mainCateCd);
    }

    /**
     * 대분류추가
     * @param request
     */
    @PostMapping("/main")
    public void createMainCategory(@Valid @RequestBody MainCategoryCreateRequest request) {
        String strCd = userService.storeCode();
        categoryService.createMainCategory(strCd, request);
    }

    /**
     * 대분류수정
     * @param mainCateCd 대분류코드
     * @param request
     */
    @PutMapping("/main/{mainCateCd}")
    public void updateMainCategory(@PathVariable String mainCateCd, @Valid @RequestBody MainCategoryUpdateRequest request) {
        String strCd = userService.storeCode();
        categoryService.updateMainCategory(strCd, mainCateCd, request);
    }

    /**
     * 대분류삭제
     * @param mainCateCd 대분류코드
     */
    @DeleteMapping("/main/{mainCateCd}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMainCategory(@PathVariable String mainCateCd) {
        String strCd = userService.storeCode();
        categoryService.deleteMainCategory(strCd, mainCateCd);
    }

    /**
     * 대분류복수삭제
     * @param request
     */
    @DeleteMapping("/main")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMainCategories(@Valid @RequestBody CategoriesDeleteRequest request) {
        String strCd = userService.storeCode();
        categoryService.deleteMainCategories(strCd, request);
    }

    /**
     * 대분류순서변경
     * @param request
     */
    @PostMapping("/main/priorities")
    public void changeMainCategoryPriorities(@Valid @RequestBody CategoryPrioritiesChangeRequest request) {
        String strCd = userService.storeCode();
        categoryService.changeMainCategoryPriorities(strCd, request);
    }

    /**
     * 중분류조회
     * @param mainCateCd 대분류코드
     * @return
     */
    @GetMapping("/main/{mainCateCd}/middle")
    public MiddleCategoriesResponse middleCategories(@PathVariable String mainCateCd) {
        String strCd = userService.storeCode();
        return categoryService.middleCategories(strCd, mainCateCd);
    }

    /**
     * 중분류상세
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @return
     */
    @GetMapping("/main/{mainCateCd}/middle/{middleCateCd}")
    public MiddleCategoryResponse middleCategory(@PathVariable String mainCateCd, @PathVariable String middleCateCd) {
        String strCd = userService.storeCode();
        return categoryService.middleCategory(strCd, mainCateCd, middleCateCd);
    }

    /**
     * 중분류추가
     * @param mainCateCd 대분류코드
     */
    @PostMapping("/main/{mainCateCd}/middle")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMiddleCategory(@PathVariable String mainCateCd, @Valid MiddleCategoryCreateRequest request) {
        String strCd = userService.storeCode();

//        MenuImageArgs args = new MenuImageArgs(request.getImage(), strCd);

//        if(!ObjectUtils.isEmpty(request.getImage())) {
//            String imageUrl = fileUploadService.uploadKioskMenuImage(args);
//            request.setImageName(imageUrl.substring(imageUrl.lastIndexOf("/") + 1));
//            request.setImageUrl(imageUrl.substring(imageUrl.indexOf("FILE_MANAGER")));
//        }

//        try {
        categoryService.createMiddleCategory(strCd, mainCateCd, request);
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
        String strCd = userService.storeCode();
        MenuImageArgs args = new MenuImageArgs(request.getImage(), strCd);

//        if(!ObjectUtils.isEmpty(request.getImage())) {
//            String imageUrl = fileUploadService.uploadKioskMenuImage(args);
//            request.setImageName(imageUrl.substring(imageUrl.lastIndexOf("/") + 1));
//            request.setImageUrl(imageUrl.substring(imageUrl.indexOf("FILE_MANAGER")));
//        }

//        try {
        categoryService.updateMiddleCategory(strCd, mainCateCd, middleCateCd, request);
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
        String strCd = userService.storeCode();
        categoryService.deleteMiddleCategory(strCd, mainCateCd, middleCateCd);
    }

    /**
     * 중분류복수삭제
     * @param mainCateCd 대분류코드
     * @param request
     */
    @DeleteMapping("/main/{mainCateCd}/middle")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMiddleCategories(@PathVariable String mainCateCd, @Valid @RequestBody CategoriesDeleteRequest request) {
        String strCd = userService.storeCode();
        categoryService.deleteMiddleCategories(strCd, mainCateCd, request);
    }

    /**
     * 중분류순서변경
     * @param mainCateCd 대분류코드
     * @param request
     */
    @PostMapping("/main/{mainCateCd}/middle/priorities")
    public void changeMiddleCategoryPriorities(@PathVariable String mainCateCd, @Valid @RequestBody CategoryPrioritiesChangeRequest request) {
        String strCd = userService.storeCode();
        categoryService.changeMiddleCategoryPriorities(strCd, mainCateCd, request);
    }

    /**
     * 소분류조회
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @return
     */
    @GetMapping("/main/{mainCateCd}/middle/{middleCateCd}/sub")
    public SubCategoriesResponse subCategories(@PathVariable String mainCateCd, @PathVariable String middleCateCd) {
        String strCd = userService.storeCode();
        return categoryService.subCategories(strCd, mainCateCd, middleCateCd);
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
        String strCd = userService.storeCode();
        return categoryService.subCategory(strCd, mainCateCd, middleCateCd, subCateCd);
    }

    /**
     * 소분류추가
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param request
     */
    @PostMapping("/main/{mainCateCd}/middle/{middleCateCd}/sub")
    public void createSubCategory(@PathVariable String mainCateCd, @PathVariable String middleCateCd, @Valid @RequestBody SubCategoryCreateRequest request) {
        String strCd = userService.storeCode();
        categoryService.createSubCategory(strCd, mainCateCd, middleCateCd, request);
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
        String strCd = userService.storeCode();
        categoryService.updateSubCategory(strCd, mainCateCd, middleCateCd, subCateCd, request);
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
        String strCd = userService.storeCode();
        categoryService.deleteSubCategory(strCd, mainCateCd, middleCateCd, subCateCd);
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
        String strCd = userService.storeCode();
        categoryService.deleteSubCategories(strCd, mainCateCd, middleCateCd, request);
    }

    /**
     * 소분류순서변경
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param request
     */
    @PostMapping("/main/{mainCateCd}/middle/{middleCateCd}/sub/priorities")
    public void changeSubCategoryPriorities(@PathVariable String mainCateCd, @PathVariable String middleCateCd, @Valid @RequestBody CategoryPrioritiesChangeRequest request) {
        String strCd = userService.storeCode();
        categoryService.changeSubCategoryPriorities(strCd, mainCateCd, middleCateCd, request);
    }
}