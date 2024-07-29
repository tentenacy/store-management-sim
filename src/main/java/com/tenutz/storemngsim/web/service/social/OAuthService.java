package com.tenutz.storemngsim.web.service.social;

import com.tenutz.storemngsim.utils.enums.SocialType;
import com.tenutz.storemngsim.web.client.common.client.OAuthClient;
import com.tenutz.storemngsim.web.client.common.dto.OAuthTokenResponse;
import com.tenutz.storemngsim.web.client.common.dto.SocialProfile;
import com.tenutz.storemngsim.web.exception.social.CSocialException.CInvalidSocialTypeException;
import com.tenutz.storemngsim.web.exception.social.CSocialException.CSocialCommunicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class OAuthService {


    private final List<OAuthClient> socialOAuthList;
    private final List<OAuthClient> kioskSocialOAuthList;
    private final HttpServletResponse response;

    @Autowired
    public OAuthService(
            @Qualifier(value = "storemngsim") List<OAuthClient> socialOAuthList,
            @Qualifier(value = "kiosksim") List<OAuthClient> kioskSocialOAuthList,
            HttpServletResponse response
    ) {
        this.socialOAuthList = socialOAuthList;
        this.kioskSocialOAuthList = kioskSocialOAuthList;
        this.response = response;
    }

    public String oauthRedirectURL(SocialType socialType) {
        OAuthClient oauthClient = this.findSocialOauthByType(socialType);
        return oauthClient.getOauthRedirectURL();
    }

    public String kioskOauthRedirectURL(SocialType socialType) {
        OAuthClient oauthClient = this.findKioskSocialOauthByType(socialType);
        return oauthClient.getOauthRedirectURL();
    }

    public void request(SocialType socialType) {
        OAuthClient oauthClient = this.findSocialOauthByType(socialType);
        String redirectURL = oauthClient.getOauthRedirectURL();
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            log.error(e.toString());
            throw new CSocialCommunicationException();
        }
    }

    public void kioskRequest(SocialType socialType) {
        OAuthClient oauthClient = this.findKioskSocialOauthByType(socialType);
        String redirectURL = oauthClient.getOauthRedirectURL();
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            log.error(e.toString());
            throw new CSocialCommunicationException();
        }
    }

    public OAuthTokenResponse tokenInfo(SocialType socialType, String code) {
        OAuthClient socialOauth = this.findSocialOauthByType(socialType);
        return socialOauth.getTokenInfo(code);
    }

    public OAuthTokenResponse kioskTokenInfo(SocialType socialType, String code) {
        OAuthClient socialOauth = this.findKioskSocialOauthByType(socialType);
        return socialOauth.getTokenInfo(code);
    }

    public SocialProfile profile(SocialType socialType, String accessToken) {
        OAuthClient socialOauth = this.findSocialOauthByType(socialType);
        return socialOauth.getProfile(accessToken);
    }

    public SocialProfile kioskProfile(SocialType socialType, String accessToken) {
        OAuthClient socialOauth = this.findKioskSocialOauthByType(socialType);
        return socialOauth.getProfile(accessToken);
    }

    private OAuthClient findSocialOauthByType(SocialType socialType) {
        return socialOAuthList.stream()
                .filter(x -> x.type() == socialType)
                .findFirst()
                .orElseThrow(CInvalidSocialTypeException::new);
    }

    private OAuthClient findKioskSocialOauthByType(SocialType socialType) {
        return kioskSocialOAuthList.stream()
                .filter(x -> x.type() == socialType)
                .findFirst()
                .orElseThrow(CInvalidSocialTypeException::new);
    }
}
