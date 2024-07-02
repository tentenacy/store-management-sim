package com.tenutz.storemngsim.web.api.controller;

import com.tenutz.storemngsim.web.api.dto.common.CommonCondition;
import com.tenutz.storemngsim.web.api.dto.help.HelpCreateRequest;
import com.tenutz.storemngsim.web.api.dto.help.HelpsResponse;
import com.tenutz.storemngsim.web.service.HelpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/helps")
@RequiredArgsConstructor
public class HelpApiController {

    private final HelpService helpService;

    @GetMapping
    public HelpsResponse helps(@Valid CommonCondition commonCond) {
        return helpService.helps(commonCond);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody HelpCreateRequest request) {
        helpService.create(request);
    }
}
