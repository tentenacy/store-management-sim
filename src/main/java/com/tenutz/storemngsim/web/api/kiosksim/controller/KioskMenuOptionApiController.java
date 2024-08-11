package com.tenutz.storemngsim.web.api.kiosksim.controller;

import com.tenutz.storemngsim.web.api.kiosksim.dto.option.KioskMenuOptionsResponse;
import com.tenutz.storemngsim.web.api.storemngsim.dto.user.StoreArgs;
import com.tenutz.storemngsim.web.service.OptionService;
import com.tenutz.storemngsim.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/app/kiosk/{kioskCode}/main-menus")
@RequiredArgsConstructor
public class KioskMenuOptionApiController {

    private final UserService userService;
    private final OptionService optionService;

    @GetMapping("/{mainMenuCode}/options")
    public KioskMenuOptionsResponse kioskMenuOptions(@PathVariable("kioskCode") String kioskCode, @PathVariable("mainMenuCode") String mainMenuCode, String subCategoryCode) {
        StoreArgs storeArgs = userService.storeArgs(kioskCode);
        return optionService.kioskMenuOptions(storeArgs.getSiteCd(), storeArgs.getStrCd(), subCategoryCode, mainMenuCode);
    }
}
