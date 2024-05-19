package com.tenutz.storemngsim.utils;

import com.tenutz.storemngsim.domain.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static User user() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
