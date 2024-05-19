package com.tenutz.storemngsim.web.api.dto.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequest {

    @NotEmpty
    private String id;

    @Length(min = 8, max = 64)
    @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[\\d]).+")
    @NotEmpty
    private String password;

    /*@Size(min = 2, max = 12)
    @NotEmpty
    private String username;*/

    @Pattern(regexp = "^kakao$|^facebook$|^naver$|^google$")
    private String provider;

    public SignupRequest(String id, String password, String provider) {
        this.id = id;
        this.password = password;
        this.provider = provider;
    }

    public SignupRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
