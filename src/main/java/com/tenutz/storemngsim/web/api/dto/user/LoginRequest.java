package com.tenutz.storemngsim.web.api.dto.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {

    @NotEmpty
    private String id;

    @Length(min = 8, max = 64)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$")
    @NotEmpty
    private String password;

    private String provider;

    public LoginRequest(String id, String password, String provider) {
        this.id = id;
        this.password = password;
        this.provider = provider;
    }

    public LoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
