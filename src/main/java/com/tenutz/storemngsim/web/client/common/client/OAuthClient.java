package com.tenutz.storemngsim.web.client.common.client;

import com.tenutz.storemngsim.utils.enums.SocialType;
import com.tenutz.storemngsim.web.client.common.dto.OAuthTokenResponse;
import com.tenutz.storemngsim.web.client.common.dto.SocialProfile;

public interface OAuthClient {

    String getOauthRedirectURL();

    OAuthTokenResponse getTokenInfo(String code);

    SocialProfile getProfile(String accessToken);

    default SocialType type() {
        if (this instanceof CommonFacebookOAuthClient) {
            return SocialType.FACEBOOK;
        } else if (this instanceof CommonGoogleOAuthClient) {
            return SocialType.GOOGLE;
        } else if (this instanceof CommonNaverOAuthClient) {
            return SocialType.NAVER;
        } else if (this instanceof CommonKaKaoOAuthClient) {
            return SocialType.KAKAO;
        } else {
            return null;
        }
    }
}
