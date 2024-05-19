package com.tenutz.storemngsim.web.api.controller.social;

import com.tenutz.storemngsim.utils.enums.SocialType;
import com.tenutz.storemngsim.web.client.dto.OAuthTokenResponse;
import com.tenutz.storemngsim.web.service.social.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthApiController {

    private final OAuthService oauthService;

    @GetMapping("/{socialType}")
    public void socialType(@PathVariable(name = "socialType") SocialType socialType) {
        log.info(">> 사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", socialType);
        oauthService.request(socialType);
    }

    @GetMapping(value = "/{socialType}/redirect")
    public OAuthTokenResponse redirect(
            @PathVariable(name = "socialType") SocialType socialType,
            @RequestParam(name = "code") String code) {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        return oauthService.tokenInfo(socialType, code);
    }
}

