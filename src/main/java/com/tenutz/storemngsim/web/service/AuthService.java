package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.config.security.JwtProvider;
import com.tenutz.storemngsim.domain.common.enums.Role;
import com.tenutz.storemngsim.domain.customer.StoreReviewRepository;
import com.tenutz.storemngsim.domain.menu.Category;
import com.tenutz.storemngsim.domain.menu.CategoryRepository;
import com.tenutz.storemngsim.domain.refreshtoken.RefreshToken;
import com.tenutz.storemngsim.domain.refreshtoken.RefreshTokenRepository;
import com.tenutz.storemngsim.domain.store.StoreMaster;
import com.tenutz.storemngsim.domain.store.StoreMasterRepository;
import com.tenutz.storemngsim.domain.user.*;
import com.tenutz.storemngsim.utils.EntityUtils;
import com.tenutz.storemngsim.utils.HttpReqRespUtils;
import com.tenutz.storemngsim.utils.enums.SocialType;
import com.tenutz.storemngsim.web.api.common.dto.TokenRequest;
import com.tenutz.storemngsim.web.api.common.dto.TokenResponse;
import com.tenutz.storemngsim.web.api.kiosksim.dto.user.KioskSocialSignupRequest;
import com.tenutz.storemngsim.web.api.storemngsim.dto.user.LoginRequest;
import com.tenutz.storemngsim.web.api.storemngsim.dto.user.SocialSignupRequest;
import com.tenutz.storemngsim.web.api.storemngsim.dto.user.UserDetailsResponse;
import com.tenutz.storemngsim.web.api.storemngsim.dto.user.UserUpdateRequest;
import com.tenutz.storemngsim.web.client.common.dto.SocialProfile;
import com.tenutz.storemngsim.web.exception.business.CInvalidValueException.CAlreadySignedupException;
import com.tenutz.storemngsim.web.exception.security.CTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
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
    private final TermsAgreementRepository termsAgreementRepository;
    private final CategoryRepository categoryRepository;
    private final StoreReviewRepository storeReviewRepository;

/*
    @Transactional
    public User signup(SignupRequest req) {
        User user = User.create(req.getBusinessNumber(), req.getBusinessNumber(), req.getPhoneNumber());
        if(userRepository.existsByUserId(user.getUserId())) {
            throw new CAlreadySignedupException();
        }
        return userRepository.save(user);
    }
*/

    @Transactional
    public void socialSignup(SocialProfile socialProfile, SocialType socialType, SocialSignupRequest request) {

        userRepository.findBySnsIdAndProvider(socialProfile.getSnsId(), socialType.name().toLowerCase())
                .ifPresent(user -> {
                    throw new CAlreadySignedupException();
                });

        storeMasterRepository.findByBusinessNumber(request.getBusinessNumber())
                .ifPresent(user -> {
                    throw new CAlreadySignedupException();
                });

        StoreMaster createdStoreMaster = storeMasterRepository.save(
                StoreMaster.create(
                        request.getBusinessNumber(),
                        request.getPhoneNumber(),
                        request.getManagerName(),
                        request.getStoreName(),
                        request.getAddress()
                )
        );

        categoryRepository.save(Category.createEmptyMainCategory(createdStoreMaster.getSiteCd(), createdStoreMaster.getStrCd()));
        categoryRepository.save(
                Category.createEmptyMiddleCategory(
                        createdStoreMaster.getSiteCd(),
                        createdStoreMaster.getStrCd(),
                        createdStoreMaster.getStrNm(),
                        createdStoreMaster.getBusinessNumber(),
                        createdStoreMaster.getStrMnger(),
                        createdStoreMaster.getTelNo(),
                        createdStoreMaster.getStrAddr()
                )
        );

        User createdUser = userRepository.saveAndFlush(
                User.createSocial(
                        passwordEncoder.encode(UUID.randomUUID().toString()),
                        socialType.name().toLowerCase(),
                        socialProfile.getSnsId(),
                        request.getManagerName(),
                        request.getBusinessNumber(),
                        request.getPhoneNumber()
                )
        );

        termsAgreementRepository.save(
                TermsAgreement.create(
                        "1",
                        createdUser.getSeq(),
                        HttpReqRespUtils.getClientIpAddressIfServletRequestExist()
                )
        );

        termsAgreementRepository.save(
                TermsAgreement.create(
                        "2",
                        createdUser.getSeq(),
                        HttpReqRespUtils.getClientIpAddressIfServletRequestExist()
                )
        );

    }

    @Transactional
    public TokenResponse kioskSocialSignupOrLogin(SocialProfile socialProfile, SocialType socialType) {

        if(userRepository.findBySnsIdAndProvider(socialProfile.getSnsId(), socialType.name().toLowerCase()).isPresent()) {
            return socialLogin(new LoginRequest(socialProfile.getSnsId(), null, socialType.name().toLowerCase()));
        }

        User createdUser = userRepository.saveAndFlush(
                User.createKioskSocial(
                        passwordEncoder.encode(UUID.randomUUID().toString()),
                        socialType.name().toLowerCase(),
                        socialProfile.getSnsId(),
                        socialProfile.getUsername()
                )
        );

        final TokenResponse tokenResponse;

        try {
            tokenResponse = socialLogin(createdUser);
        } catch (Exception e) {
            userRepository.delete(createdUser);
            throw e;
        }

        return tokenResponse;
    }


/*
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
*/

    @Transactional
    public TokenResponse socialLogin(LoginRequest request) {
        User user = userRepository.findBySnsIdAndProvider(request.getId(), request.getProvider())
                .orElseThrow(CUserNotFoundException::new);
        return socialLogin(user);
    }

    @Transactional
    public TokenResponse socialLogin(User user) {
        refreshTokenRepository.findByKey(user.getSeq().toString()).ifPresent(refreshTokenRepository::delete);
        TokenResponse tokenResponse = jwtProvider.createToken(user.getSeq().toString(), user.getRoles().stream().map(Role::getValue).collect(Collectors.toList()));
        refreshTokenRepository.save(RefreshToken.create(user.getSeq().toString(), tokenResponse.getRefreshToken()));
        return tokenResponse;
    }

    @Transactional
    public TokenResponse kioskSocialLogin(LoginRequest request) {
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

        log.debug(newCreatedToken.getAccessToken());

        return newCreatedToken;
    }

    public UserDetailsResponse userDetails() {
        User user = EntityUtils.userThrowable();
        StoreMaster foundStoreMaster = storeMasterRepository.findByBusinessNumber(user.getBusinessNo()).orElseThrow(CStoreMasterNotFoundException::new);
        return new UserDetailsResponse(
                user.getSeq().toString(),
                foundStoreMaster.getSiteCd(),
                foundStoreMaster.getStrCd(),
                user.getUserId(),
                user.getUsername(),
                user.getProvider(),
                user.getBusinessNo(),
                user.getContact(),
                foundStoreMaster.getStrNm(),
                foundStoreMaster.getStrAddr(),
                foundStoreMaster.getKioskCd(),
                user.getRegisteredAt()
        );
    }

    @Transactional
    public void update(Integer userSeq, UserUpdateRequest request) {
        User foundUser = userRepository.findById(userSeq)
                .orElseThrow(CUserNotFoundException::new);
        StoreMaster foundStoreMaster = storeMasterRepository.findByBusinessNumber(request.getBusinessNumber())
                .orElseThrow(CStoreMasterNotFoundException::new);
        Category foundCategory = categoryRepository.middleCategory(foundStoreMaster.getSiteCd(), foundStoreMaster.getStrCd(), "2000", "3000")
                .orElseThrow(CCategoryNotFoundException::new);

        String prevCateName = foundCategory.getCateName();

        foundUser.update(request.getBusinessNumber(), request.getUsername(), request.getPhoneNumber());
        foundStoreMaster.update(request.getBusinessNumber(), request.getUsername(), request.getPhoneNumber(), request.getStoreName(), request.getAddress());
        foundCategory.updateMiddleCategory(request.getStoreName(), request.getBusinessNumber(), request.getUsername(), request.getPhoneNumber(), request.getAddress());

        storeReviewRepository.storeReviews(foundStoreMaster.getSiteCd(), foundStoreMaster.getStrCd(), "3000", prevCateName).forEach(review -> {
            review.updateAsMiddleCategory(foundCategory.getCateName());
            storeReviewRepository.save(review);
        });
    }

    @Transactional
    public void delete(Integer userSeq) {
        userRepository.deleteById(userSeq);
        termsAgreementRepository.deleteByUserSeq(userSeq);
        refreshTokenRepository.deleteByKey(userSeq.toString());
    }
}
