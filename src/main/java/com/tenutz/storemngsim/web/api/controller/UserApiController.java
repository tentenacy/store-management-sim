package com.tenutz.storemngsim.web.api.controller;

import com.tenutz.storemngsim.utils.enums.SocialType;
import com.tenutz.storemngsim.web.api.dto.common.TokenRequest;
import com.tenutz.storemngsim.web.api.dto.common.TokenResponse;
import com.tenutz.storemngsim.web.api.dto.user.*;
import com.tenutz.storemngsim.web.client.dto.SocialProfile;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException.CUserNotFoundException;
import com.tenutz.storemngsim.web.service.AuthService;
import com.tenutz.storemngsim.web.service.social.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApiController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final OAuthService oauthService;


/*
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody @Validated SignupRequest signupRequest) {
        signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        authService.signup(signupRequest);
    }
*/

    @PostMapping("/social/{socialType}")
    @ResponseStatus(HttpStatus.CREATED)
    public void socialSignup(@PathVariable(name = "socialType") SocialType socialType,
                             @RequestBody @Validated SocialSignupRequest request) {

        //구글은 access_token 대신 id_token 값으로
        SocialProfile socialProfile = oauthService.profile(socialType, request.getAccessToken());

        //소셜 프로필이 없는 경우 에러                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             a
        if (ObjectUtils.isEmpty(socialProfile)) throw new CUserNotFoundException();

/*
        //동의 항목(이메일)에 동의하지 않은 경우 연결 끊은 후 에러
        if (!StringUtils.hasText(socialProfile.getEmail())) {
            throw new CSocialAgreementException();
        }
*/

        authService.socialSignup(socialProfile, socialType, request);
    }

/*
    @PostMapping("/token")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse login(@RequestBody @Validated LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
*/

    @PostMapping("/social/{socialType}/token")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse socialLogin(@PathVariable(name = "socialType") SocialType socialType,
                                     @RequestBody @Validated SocialLoginRequest request) {

        SocialProfile socialProfile = oauthService.profile(socialType, request.getAccessToken());

        //소셜 프로필이 없는 경우 에러
        if(ObjectUtils.isEmpty(socialProfile)) throw new CUserNotFoundException();

        return authService.socialLogin(new LoginRequest(socialProfile.getSnsId(), null, socialType.name().toLowerCase()));
    }

    @PostMapping("/token/expiration")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse reissue(@RequestBody @Validated TokenRequest request) {
        return authService.reissue(request);
    }

    @GetMapping("/details")
    public UserDetailsResponse userDetails() {
        return authService.userDetails();
    }

    @DeleteMapping("/{userSeq}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer userSeq) {
        authService.delete(userSeq);
    }

}
