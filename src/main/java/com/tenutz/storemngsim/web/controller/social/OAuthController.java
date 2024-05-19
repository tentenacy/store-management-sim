package com.tenutz.storemngsim.web.controller.social;

import com.tenutz.storemngsim.utils.enums.SocialType;
import com.tenutz.storemngsim.web.service.social.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@CrossOrigin
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oauthService;

    @GetMapping("/login")
    public String socialLogin(Model model) {

        model.addAttribute("kakaoLoginUri", oauthService.oauthRedirectURL(SocialType.KAKAO));
        model.addAttribute("googleLoginUri", oauthService.oauthRedirectURL(SocialType.GOOGLE));
        model.addAttribute("naverLoginUri", oauthService.oauthRedirectURL(SocialType.NAVER));
        model.addAttribute("facebookLoginUri", oauthService.oauthRedirectURL(SocialType.FACEBOOK));

        return "social/login";
    }
}
