package com.tenutz.storemngsim.web.client.kiosksim;

import com.tenutz.storemngsim.web.client.common.CommonFacebookOAuthClient;
import com.tenutz.storemngsim.web.client.common.OAuthClient;
import com.tenutz.storemngsim.web.client.common.dto.FacebookProfile;
import com.tenutz.storemngsim.web.client.common.dto.OAuthTokenResponse;
import com.tenutz.storemngsim.web.client.common.dto.SocialProfile;
import com.tenutz.storemngsim.web.exception.social.CSocialException.CSocialCommunicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component(value = "kiosksimFacebookOAuthClient")
@Qualifier(value = "kiosksim")
@RequiredArgsConstructor
public class FacebookOAuthClient implements CommonFacebookOAuthClient {

    private final WebClient webClient;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${kiosk-sim.social.facebook.client-id}")
    private String facebookClientId;

    @Value("${kiosk-sim.social.facebook.client-secret}")
    private String facebookClientSecret;

    @Value("${kiosk-sim.social.facebook.redirect}")
    private String facebookRedirectUri;

    @Value("${kiosk-sim.social.facebook.url.login}")
    private String facebookLoginUrl;

    @Value("${kiosk-sim.social.facebook.url.token}")
    private String facebookTokenUrl;

    @Value("${kiosk-sim.social.facebook.url.profile}")
    private String facebookProfileUrl;

    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();

        params.put("redirect_uri", baseUrl + facebookRedirectUri);
        params.put("client_id", facebookClientId);
        params.put("scope", "email,public_profile");

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return facebookLoginUrl + "?" + parameterString;
    }

    @Override
    public OAuthTokenResponse getTokenInfo(String code) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", facebookClientId);
        params.add("client_secret", facebookClientSecret);
        params.add("redirect_uri", baseUrl + facebookRedirectUri);
        params.add("code", code);

        return webClient.get()
                .uri(facebookTokenUrl, builder -> builder.queryParams(params).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new CSocialCommunicationException()))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new CSocialCommunicationException()))
                .bodyToMono(OAuthTokenResponse.class)
                .block();
    }

    @Override
    public SocialProfile getProfile(String accessToken) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("fields", "name,picture,email");
        params.add("access_token", accessToken);

        FacebookProfile facebookProfile = webClient.get()
                .uri(facebookProfileUrl, builder -> builder.queryParams(params).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new CSocialCommunicationException()))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new CSocialCommunicationException()))
                .bodyToMono(FacebookProfile.class)
                .block();

        return new SocialProfile(facebookProfile.getId(), facebookProfile.getEmail(), facebookProfile.getName(), facebookProfile.getPicture().getData().getUrl());
    }
}
