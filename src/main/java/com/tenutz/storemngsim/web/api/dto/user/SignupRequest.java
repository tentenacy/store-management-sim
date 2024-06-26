package com.tenutz.storemngsim.web.api.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignupRequest {

    @NotEmpty
    private String businessNumber;

    @Length(min = 8, max = 64)
    @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[\\d]).+")
    @NotEmpty
    private String password;

    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String ownerName;

    /*@Size(min = 2, max = 12)
    @NotEmpty
    private String username;*/
}
