package com.tenutz.storemngsim.web.client.storemngsim;

import com.tenutz.storemngsim.web.client.common.CommonNaverOAuthClient;
import com.tenutz.storemngsim.web.client.common.OAuthClient;
import com.tenutz.storemngsim.web.client.common.dto.NaverProfile;
import com.tenutz.storemngsim.web.client.common.dto.OAuthTokenResponse;
import com.tenutz.storemngsim.web.client.common.dto.SocialProfile;
import com.tenutz.storemngsim.web.exception.social.CSocialException.CSocialCommunicationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
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


@Component
@Qualifier(value = "storemngsim")
@RequiredArgsConstructor
public class NaverOAuthClient implements CommonNaverOAuthClient {

    private final WebClient webClient;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${store-mng-sim.social.naver.client-id}")
    private String naverClientId;

    @Value("${store-mng-sim.social.naver.client-secret}")
    private String naverClientSecret;

    @Value("${store-mng-sim.social.naver.redirect}")
    private String naverRedirectUri;

    @Value("${store-mng-sim.social.naver.url.login}")
    private String naverLoginUrl;

    @Value("${store-mng-sim.social.naver.url.token}")
    private String naverTokenUrl;

    @Value("${store-mng-sim.social.naver.url.profile}")
    private String naverProfileUrl;

    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();

        params.put("response_type", "code");
        params.put("client_id", naverClientId);
        params.put("redirect_uri", baseUrl + naverRedirectUri);
        params.put("state", RandomStringUtils.random(16, true, true));

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return naverLoginUrl + "?" + parameterString;
    }

    @Override
    public OAuthTokenResponse getTokenInfo(String code) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", naverClientId);
        params.add("client_secret", naverClientSecret);
        params.add("code", code);
        params.add("state", RandomStringUtils.random(16, true, true));

        return webClient.post()
                .uri(naverTokenUrl, builder -> builder.queryParams(params).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new CSocialCommunicationException()))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new CSocialCommunicationException()))
                .bodyToMono(OAuthTokenResponse.class)
                .block();
    }

    @Override
    public SocialProfile getProfile(String accessToken) {
        NaverProfile naverProfile = webClient.post()
                .uri(naverProfileUrl)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new CSocialCommunicationException()))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new CSocialCommunicationException()))
                .bodyToMono(NaverProfile.class)
                .block();

        return new SocialProfile(naverProfile.getResponse().getId(), naverProfile.getResponse().getEmail(), naverProfile.getResponse().getNickname(), naverProfile.getResponse().getProfile_image());
    }
}
