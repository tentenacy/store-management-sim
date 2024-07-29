package com.tenutz.storemngsim.web.api.storemngsim.controller;

import com.tenutz.storemngsim.web.api.storemngsim.dto.term.TermResponse;
import com.tenutz.storemngsim.web.api.storemngsim.dto.term.TermsResponse;
import com.tenutz.storemngsim.web.service.TermsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/terms")
@RequiredArgsConstructor
public class TermApiController {

    private final TermsService termsService;

    @GetMapping
    public TermsResponse terms() {
        return termsService.terms();
    }

    @GetMapping("/{termsCd}")
    public TermResponse termsDetails(@PathVariable String termsCd) {
        return termsService.termsDetails(termsCd);
    }
}
