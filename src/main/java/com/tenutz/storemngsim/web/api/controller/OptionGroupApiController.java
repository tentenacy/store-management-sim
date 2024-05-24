package com.tenutz.storemngsim.web.api.controller;

import com.tenutz.storemngsim.web.api.dto.optiongroup.OptionGroupResponse;
import com.tenutz.storemngsim.web.api.dto.optiongroup.OptionGroupsResponse;
import com.tenutz.storemngsim.web.service.OptionGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/stores/{strCd}/option-groups")
@RequiredArgsConstructor
public class OptionGroupApiController {

    private final OptionGroupService optionGroupService;

    /**
     * 옵션그룹조회
     * @param strCd 가맹점코드
     * @return
     */
    @GetMapping
    public OptionGroupsResponse optionGroups(@PathVariable String strCd) {
        return optionGroupService.optionGroups(strCd);
    }

    /**
     * 옵션그룹상세
     * @param strCd         가맹점코드
     * @param optionGroupCd 옵션그룹코드
     * @return
     */
    @GetMapping("/{optionGroupCd}")
    public OptionGroupResponse optionGroup(@PathVariable String strCd, @PathVariable String optionGroupCd) {
        return optionGroupService.option(strCd, optionGroupCd);
    }
}
