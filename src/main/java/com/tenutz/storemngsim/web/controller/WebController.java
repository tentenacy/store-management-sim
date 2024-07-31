package com.tenutz.storemngsim.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@CrossOrigin
@RequestMapping
@RequiredArgsConstructor
public class WebController {

    @GetMapping("/addr")
    public String address() {
        return "addr";
    }
}
