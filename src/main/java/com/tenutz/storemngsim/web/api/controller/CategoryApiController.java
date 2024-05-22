package com.tenutz.storemngsim.web.api.controller;

import com.tenutz.storemngsim.web.api.dto.category.*;
import com.tenutz.storemngsim.web.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/stores/{strCd}/categories")
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    /**
     * 대분류조회
     * @param strCd 가맹점코드
     * @return
     */
    @GetMapping("/main")
    public MainCategoriesResponse mainCategories(@PathVariable String strCd) {
        return categoryService.mainCategories(strCd);
    }

    /**
     * 대분류상세
     * @param strCd 가맹점코드
     * @param mainCateCd 대분류코드
     * @return
     */
    @GetMapping("/main/{mainCateCd}")
    public MainCategoryResponse mainCategory(@PathVariable String strCd, @PathVariable String mainCateCd) {
        return categoryService.mainCategory(strCd, mainCateCd);
    }

    /**
     * 대분류추가
     * @param strCd 가맹점코드
     * @param request
     */
    @PostMapping("/main")
    public void createMainCategory(@PathVariable String strCd, @Valid @RequestBody MainCategoryCreateRequest request) {
        categoryService.createMainCategory(strCd, request);
    }

    /**
     * 대분류수정
     * @param strCd 가맹점코드
     * @param mainCateCd 대분류코드
     * @param request
     */
    @PutMapping("/main/{mainCateCd}")
    public void updateMainCategory(@PathVariable String strCd, @PathVariable String mainCateCd, @Valid @RequestBody MainCategoryUpdateRequest request) {
        categoryService.updateMainCategory(strCd, mainCateCd, request);
    }

    /**
     * 대분류삭제
     * @param strCd 가맹점코드
     * @param mainCateCd 대분류코드
     */
    @DeleteMapping("/main/{mainCateCd}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMainCategory(@PathVariable String strCd, @PathVariable String mainCateCd) {
        categoryService.deleteMainCategory(strCd, mainCateCd);
    }

    /**
     * 대분류복수삭제
     * @param strCd 가맹점코드
     * @param request
     */
    @DeleteMapping("/main")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMainCategories(@PathVariable String strCd, @Valid @RequestBody MainCategoriesDeleteRequest request) {
        categoryService.deleteMainCategories(strCd, request);
    }

    /**
     * 대분류순서변경
     * @param strCd 가맹점코드
     * @param request
     */
    @PostMapping("/main/priorities")
    public void changeMainCategoryPriorities(@PathVariable String strCd, @Valid @RequestBody MainCategoryPrioritiesChangeRequest request) {
        categoryService.changeMainCategoryPriorities(strCd, request);
    }

    /**
     * 중분류조회
     * @param strCd 가맹점코드
     * @param mainCateCd 대분류코드
     * @return
     */
    @GetMapping("/main/{mainCateCd}/middle")
    public MiddleCategoriesResponse middleCategories(@PathVariable String strCd, @PathVariable String mainCateCd) {
        return categoryService.middleCategories(strCd, mainCateCd);
    }

    /**
     * 중분류상세
     * @param strCd 가맹점코드
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @return
     */
    @GetMapping("/main/{mainCateCd}/middle/{middleCateCd}")
    public MiddleCategoryResponse middleCategory(@PathVariable String strCd, @PathVariable String mainCateCd, @PathVariable String middleCateCd) {
        return categoryService.middleCategory(strCd, mainCateCd, middleCateCd);
    }

    /**
     * 중분류추가
     * @param strCd 가맹점코드
     * @param mainCateCd 대분류코드
     * @param request
     */
    @PostMapping("/main/{mainCateCd}/middle")
    public void createMiddleCategory(@PathVariable String strCd, @PathVariable String mainCateCd, @Valid @RequestBody MiddleCategoryCreateRequest request) {
        categoryService.createMiddleCategory(strCd, mainCateCd, request);
    }

    /**
     * 중분류수정
     * @param strCd 가맹점코드
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param request
     */
    @PutMapping("/main/{mainCateCd}/middle/{middleCateCd}")
    public void updateMiddleCategory(@PathVariable String strCd, @PathVariable String mainCateCd, @PathVariable String middleCateCd, @Valid @RequestBody MiddleCategoryUpdateRequest request) {
        categoryService.updateMiddleCategory(strCd, mainCateCd, middleCateCd, request);
    }

    @DeleteMapping("/main/{mainCateCd}/middle/{middleCateCd}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMiddleCategory(@PathVariable String strCd, @PathVariable String mainCateCd, @PathVariable String middleCateCd) {
        categoryService.deleteMiddleCategory(strCd, mainCateCd, middleCateCd);
    }

    /**
     * 소분류조회
     * @param strCd 가맹점코드
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @return
     */
    @GetMapping("/main/{mainCateCd}/middle/{middleCateCd}/sub")
    public SubCategoriesResponse subCategories(@PathVariable String strCd, @PathVariable String mainCateCd, @PathVariable String middleCateCd) {
        return categoryService.subCategories(strCd, mainCateCd, middleCateCd);
    }

    /**
     * 소분류상세
     * @param strCd 가맹점코드
     * @param mainCateCd 대분류코드
     * @param middleCateCd 중분류코드
     * @param subCateCd 소분류코드
     * @return
     */
    @GetMapping("/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}")
    public SubCategoryResponse subCategory(@PathVariable String strCd, @PathVariable String mainCateCd, @PathVariable String middleCateCd, @PathVariable String subCateCd) {
        return categoryService.subCategory(strCd, mainCateCd, middleCateCd, subCateCd);
    }
}
