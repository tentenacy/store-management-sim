package com.tenutz.storemngsim.web.api.controller;

import com.tenutz.storemngsim.web.api.dto.category.MainCategoriesResponse;
import com.tenutz.storemngsim.web.api.dto.category.MainCategoryResponse;
import com.tenutz.storemngsim.web.api.dto.category.MiddleCategoriesResponse;
import com.tenutz.storemngsim.web.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/main/{mainCateCd}/middle")
    public MiddleCategoriesResponse middleCategories(@PathVariable String strCd, @PathVariable String mainCateCd) {
        return categoryService.middleCategories(strCd, mainCateCd);
    }
}
