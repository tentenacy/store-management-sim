package com.tenutz.storemngsim.web.api.controller;

import com.tenutz.storemngsim.web.api.dto.option.OptionCreateRequest;
import com.tenutz.storemngsim.web.api.dto.option.OptionResponse;
import com.tenutz.storemngsim.web.api.dto.option.OptionsResponse;
import com.tenutz.storemngsim.web.service.OptionGroupService;
import com.tenutz.storemngsim.web.service.OptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/stores/{strCd}/options")
@RequiredArgsConstructor
public class OptionApiController {

    private final OptionService optionService;
    private final OptionGroupService optionGroupService;

    /**
     * 옵션조회
     * @param strCd 가맹점코드
     * @return
     */
    @GetMapping
    public OptionsResponse options(@PathVariable String strCd) {
        return optionService.options(strCd);
    }

    /**
     * 옵션상세
     * @param strCd 가맹점코드
     * @param optionCd 옵션코드
     * @return
     */
    @GetMapping("/{optionCd}")
    public OptionResponse option(@PathVariable String strCd, @PathVariable String optionCd) {
        return optionService.option(strCd, optionCd);
    }

    /**
     * 옵션추가
     * @param strCd 가맹점코드
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOption(@PathVariable String strCd, @Valid @RequestBody OptionCreateRequest request) {
        optionService.createOption(strCd, request);
    }
}
