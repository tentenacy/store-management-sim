package com.tenutz.storemngsim.web.api.controller;

import com.tenutz.storemngsim.web.api.dto.option.OptionsResponse;
import com.tenutz.storemngsim.web.service.OptionGroupService;
import com.tenutz.storemngsim.web.service.OptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
