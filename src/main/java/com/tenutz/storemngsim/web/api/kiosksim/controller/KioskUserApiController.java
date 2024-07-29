package com.tenutz.storemngsim.web.api.kiosksim.controller;

import com.tenutz.storemngsim.utils.enums.SocialType;
import com.tenutz.storemngsim.web.api.common.dto.TokenRequest;
import com.tenutz.storemngsim.web.api.common.dto.TokenResponse;
import com.tenutz.storemngsim.web.api.kiosksim.dto.user.KioskSocialRequest;
import com.tenutz.storemngsim.web.client.common.dto.SocialProfile;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException;
import com.tenutz.storemngsim.web.service.AuthService;
import com.tenutz.storemngsim.web.service.StoreService;
import com.tenutz.storemngsim.web.service.social.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/app/kiosk/users")
@RequiredArgsConstructor
public class KioskUserApiController {

    private final AuthService authService;
    private final OAuthService oauthService;
    private final StoreService storeService;


    @PostMapping("/social/{socialType}/token")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse socialLoginOrSignup(@PathVariable(name = "socialType") SocialType socialType,
                                    @RequestBody @Validated KioskSocialRequest request) {

        //구글은 access_token 대신 id_token 값으로
        SocialProfile socialProfile = oauthService.kioskProfile(socialType, request.getAccessToken());

        //소셜 프로필이 없는 경우 에러                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             a
        if (ObjectUtils.isEmpty(socialProfile)) throw new CEntityNotFoundException.CUserNotFoundException();

        return authService.kioskSocialSignupOrLogin(socialProfile, socialType);
    }

    @PostMapping("/token/expiration")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse reissue(@RequestBody @Validated TokenRequest request) {
        return authService.reissue(request);
    }

    /**
     * 매장키오스크확인
     * @param kioskCode 키오스크 코드
     */
    @GetMapping("/existing-stores/kiosk/{kioskCode}")
    public void storeExists(@PathVariable(name = "kioskCode") String kioskCode) {
        storeService.existsByKioskCode(kioskCode);
    }
}
