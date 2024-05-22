package com.tenutz.storemngsim.web.api.controller;

import com.tenutz.storemngsim.web.api.dto.category.*;
import com.tenutz.storemngsim.web.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/stores/{strCd}/categories")
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    @GetMapping("/main")
    public MainCategoriesResponse mainCategories(@PathVariable String strCd) {
        return categoryService.mainCategories(strCd);
    }

    @GetMapping("/main/{mainCateCd}")
    public MainCategoryResponse mainCategory(@PathVariable String strCd, @PathVariable String mainCateCd) {
        return categoryService.mainCategory(strCd, mainCateCd);
    }

    /*@PostMapping("/main")
    public void createMainCategories(@Valid @RequestBody MainCategoryCreateRequest request) {
        return categoryService.createMainCategories();
    }*/

    @GetMapping("/main/{mainCateCd}/middle")
    public MiddleCategoriesResponse middleCategories(@PathVariable String strCd, @PathVariable String mainCateCd) {
        return categoryService.middleCategories(strCd, mainCateCd);
    }

    @GetMapping("/main/{mainCateCd}/middle/{middleCateCd}")
    public MiddleCategoryResponse middleCategory(@PathVariable String strCd, @PathVariable String mainCateCd, @PathVariable String middleCateCd) {
        return categoryService.middleCategory(strCd, mainCateCd, middleCateCd);
    }

    @GetMapping("/main/{mainCateCd}/middle/{middleCateCd}/sub")
    public SubCategoriesResponse subCategories(@PathVariable String strCd, @PathVariable String mainCateCd, @PathVariable String middleCateCd) {
        return categoryService.subCategories(strCd, mainCateCd, middleCateCd);
    }

    @GetMapping("/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}")
    public SubCategoryResponse subCategory(@PathVariable String strCd, @PathVariable String mainCateCd, @PathVariable String middleCateCd, @PathVariable String subCateCd) {
        return categoryService.subCategory(strCd, mainCateCd, middleCateCd, subCateCd);
    }
}
