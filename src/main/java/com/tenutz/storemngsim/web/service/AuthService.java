package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.config.security.JwtProvider;
import com.tenutz.storemngsim.domain.common.enums.Role;
import com.tenutz.storemngsim.domain.refreshtoken.RefreshToken;
import com.tenutz.storemngsim.domain.refreshtoken.RefreshTokenRepository;
import com.tenutz.storemngsim.domain.store.StoreMaster;
import com.tenutz.storemngsim.domain.store.StoreMasterRepository;
import com.tenutz.storemngsim.domain.user.User;
import com.tenutz.storemngsim.domain.user.UserRepository;
import com.tenutz.storemngsim.utils.EntityUtils;
import com.tenutz.storemngsim.utils.SecurityUtils;
import com.tenutz.storemngsim.utils.enums.SocialType;
import com.tenutz.storemngsim.web.api.dto.common.TokenRequest;
import com.tenutz.storemngsim.web.api.dto.common.TokenResponse;
import com.tenutz.storemngsim.web.api.dto.user.LoginRequest;
import com.tenutz.storemngsim.web.api.dto.user.SignupRequest;
import com.tenutz.storemngsim.web.api.dto.user.SocialSignupRequest;
import com.tenutz.storemngsim.web.api.dto.user.UserDetailsResponse;
import com.tenutz.storemngsim.web.client.dto.SocialProfile;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException;
import com.tenutz.storemngsim.web.exception.business.CInvalidValueException;
import com.tenutz.storemngsim.web.exception.business.CInvalidValueException.CAlreadySignedupException;
import com.tenutz.storemngsim.web.exception.security.CTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

import static com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final StoreMasterRepository storeMasterRepository;

    @Transactional
    public User signup(SignupRequest req) {
        User user = User.create(req.getBusinessNumber(), req.getPassword(), req.getOwnerName(), req.getBusinessNumber(), req.getPhoneNumber());
        if(userRepository.existsByUserId(user.getUserId())) {
            throw new CAlreadySignedupException();
        }
        return userRepository.save(user);
    }

    @Transactional
    public void socialSignup(SocialProfile socialProfile, SocialType socialType, SocialSignupRequest request) {
        userRepository.findBySnsIdAndProvider(socialProfile.getSnsId(), socialType.name().toLowerCase())
                .ifPresent(user -> {
                    throw new CAlreadySignedupException();
                });
        userRepository.save(
                User.createSocial(
                        request.getBusinessNumber(),
                        request.getPassword(),
                        socialType.name().toLowerCase(),
                        socialProfile.getSnsId(),
                        request.getOwnerName(),
                        request.getBusinessNumber(),
                        request.getPhoneNumber()
                )
        );
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByUserId(request.getId()).orElseThrow(CInvalidValueException.CLoginFailedException::new);
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CInvalidValueException.CLoginFailedException();
        }
        refreshTokenRepository.findByKey(user.getSeq().toString()).ifPresent(refreshTokenRepository::delete);
        TokenResponse tokenResponse = jwtProvider.createToken(user.getSeq().toString(), user.getRoles().stream().map(Role::getValue).collect(Collectors.toList()));
        refreshTokenRepository.save(RefreshToken.create(user.getSeq().toString(), tokenResponse.getRefreshToken()));
        return tokenResponse;
    }

    @Transactional
    public TokenResponse socialLogin(LoginRequest request) {
        User user = userRepository.findBySnsIdAndProvider(request.getId(), request.getProvider())
                .orElseThrow(CUserNotFoundException::new);
        refreshTokenRepository.findByKey(user.getSeq().toString()).ifPresent(refreshTokenRepository::delete);
        TokenResponse tokenResponse = jwtProvider.createToken(user.getSeq().toString(), user.getRoles().stream().map(Role::getValue).collect(Collectors.toList()));
        refreshTokenRepository.save(RefreshToken.create(user.getSeq().toString(), tokenResponse.getRefreshToken()));
        return tokenResponse;
    }

    /**
     * TokenRequest를 통해 액세스 토큰 재발급 요청
     * * 리프레시 토큰 만료 검증 -> 만료 시 재로그인 요청
     */
    @Transactional
    public TokenResponse reissue(TokenRequest request) {

        //리프레시 토큰 만료
        if(!jwtProvider.validationToken(request.getRefreshToken())) {
            throw new CTokenException.CRefreshTokenException();
        }

        String accessToken = request.getAccessToken();
        Authentication authentication = jwtProvider.getAuthentication(accessToken);
        User foundUser = EntityUtils.userThrowable(userRepository, ((User)authentication.getPrincipal()).getSeq());

        //리프레시 토큰 없음
        RefreshToken refreshToken = refreshTokenRepository.findByKey(foundUser.getSeq().toString())
                .orElseThrow(CTokenException.CRefreshTokenException::new);

        //리프레시 토큰 불일치
        if(!refreshToken.getToken().equals(request.getRefreshToken())) {
            throw new CTokenException.CRefreshTokenException();
        }

        TokenResponse newCreatedToken = jwtProvider.createToken(foundUser.getSeq().toString(), foundUser.getRoles().stream().map(Role::getValue).collect(Collectors.toList()));
        refreshToken.update(newCreatedToken.getRefreshToken());

        return newCreatedToken;
    }

    public UserDetailsResponse userDetails() {
        User user = SecurityUtils.user().orElseThrow(CUserNotFoundException::new);
        StoreMaster foundStoreMaster = storeMasterRepository.findByBusinessNumber(user.getBusinessNo()).orElseThrow(CStoreMasterNotFoundException::new);
        return new UserDetailsResponse(
                user.getSeq().toString(),
                foundStoreMaster.getSiteCd(),
                foundStoreMaster.getStrCd(),
                user.getUsername(),
                user.getBusinessNo(),
                user.getContact(),
                user.getUserId(),
                user.getProvider(),
                user.getRegisteredAt(),
                user.getReceiveYn()
        );
    }
}
