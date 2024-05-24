package com.tenutz.storemngsim.web.api.controller;

import com.tenutz.storemngsim.web.api.dto.option.OptionsResponse;
import com.tenutz.storemngsim.web.api.dto.optiongroup.OptionGroupCreateRequest;
import com.tenutz.storemngsim.web.api.dto.optiongroup.OptionGroupResponse;
import com.tenutz.storemngsim.web.api.dto.optiongroup.OptionGroupUpdateRequest;
import com.tenutz.storemngsim.web.api.dto.optiongroup.OptionGroupsResponse;
import com.tenutz.storemngsim.web.service.OptionGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    /**
     * 옵션그룹추가
     * @param strCd   가맹점코드
     * @param request
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOptionGroup(@PathVariable String strCd, @Valid @RequestBody OptionGroupCreateRequest request) {
        optionGroupService.create(strCd, request);
    }

    /**
     * 옵션그룹수정
     * @param strCd         가맹점코드
     * @param optionGroupCd 옵션그룹코드
     * @param request
     */
    @PutMapping("/{optionGroupCd}")
    public void updateOptionGroup(@PathVariable String strCd, @PathVariable String optionGroupCd, @Valid @RequestBody OptionGroupUpdateRequest request) {
        optionGroupService.update(strCd, optionGroupCd, request);
    }

    /**
     * 옵션그룹삭제
     * @param strCd         가맹점코드
     * @param optionGroupCd 옵션그룹코드
     */
    @DeleteMapping("/{optionGroupCd}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOptionGroup(@PathVariable String strCd, @PathVariable String optionGroupCd) {
        optionGroupService.delete(strCd, optionGroupCd);
    }

}
