package com.tenutz.storemngsim.web.client.kiosksim;

import com.google.gson.Gson;
import com.tenutz.storemngsim.web.client.common.CommonKaKaoOAuthClient;
import com.tenutz.storemngsim.web.client.common.OAuthClient;
import com.tenutz.storemngsim.web.client.common.dto.KakaoProfile;
import com.tenutz.storemngsim.web.client.common.dto.OAuthTokenResponse;
import com.tenutz.storemngsim.web.client.common.dto.SocialProfile;
import com.tenutz.storemngsim.web.exception.social.CSocialException.CSocialCommunicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component(value = "kiosksimKaKaoOAuthClient")
@Qualifier(value = "kiosksim")
@RequiredArgsConstructor
public class KaKaoOAuthClient implements CommonKaKaoOAuthClient {

    private final RestTemplate restTemplate;
    private final Gson gson;
    private final WebClient webClient;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${kiosk-sim.social.kakao.client-id}")
    private String kakaoClientId;

    @Value("${kiosk-sim.social.kakao.redirect}")
    private String kakaoRedirectUri;

    @Value("${kiosk-sim.social.kakao.url.login}")
    private String kakaoLoginUrl;

    @Value("${kiosk-sim.social.kakao.url.token}")
    private String kakaoTokenUrl;

    @Value("${kiosk-sim.social.kakao.url.profile}")
    private String kakaoProfileUrl;

    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", kakaoClientId);
        params.put("redirect_uri", baseUrl + kakaoRedirectUri);
        params.put("response_type", "code");

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return kakaoLoginUrl + "?" + parameterString;
    }

    @Override
    public OAuthTokenResponse getTokenInfo(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", baseUrl + kakaoRedirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(kakaoTokenUrl, request, String.class);
        if (response.getStatusCode() == HttpStatus.OK)
            return gson.fromJson(response.getBody(), OAuthTokenResponse.class);
        throw new CSocialCommunicationException();
    }

    @Override
    public SocialProfile getProfile(String accessToken) {

        KakaoProfile kakaoProfile = webClient.post()
                .uri(kakaoProfileUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", "Bearer " + accessToken)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is4xxClientError()) {
                        return clientResponse
                                .createException()
                                .flatMap(Mono::error);
                    }

                    if (clientResponse.statusCode().is5xxServerError()) {
                        return clientResponse
                                .createException()
                                .flatMap(Mono::error);
                    }

                    return clientResponse.bodyToMono(KakaoProfile.class);
                })
                .onErrorResume(error -> {
                    log.error(error.getMessage());
                    return Mono.error(new CSocialCommunicationException());
                })
                .block();

        return new SocialProfile(kakaoProfile.getId().toString(), kakaoProfile.getKakao_account().getEmail(), kakaoProfile.getKakao_account().getName(), kakaoProfile.getKakao_account().getProfile().getProfile_image_url());
    }
}
