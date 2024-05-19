package com.tenutz.storemngsim.web.client;

import com.tenutz.storemngsim.utils.enums.SocialType;
import com.tenutz.storemngsim.web.client.dto.OAuthTokenResponse;
import com.tenutz.storemngsim.web.client.dto.SocialProfile;

public interface OAuthClient {

    String getOauthRedirectURL();

    OAuthTokenResponse getTokenInfo(String code);

    SocialProfile getProfile(String accessToken);

    default SocialType type() {
        if (this instanceof FacebookOAuthClient) {
            return SocialType.FACEBOOK;
        } else if (this instanceof GoogleOAuthClient) {
            return SocialType.GOOGLE;
        } else if (this instanceof NaverOAuthClient) {
            return SocialType.NAVER;
        } else if (this instanceof KaKaoOAuthClient) {
            return SocialType.KAKAO;
        } else {
            return null;
        }
    }
}
