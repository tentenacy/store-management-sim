package com.tenutz.storemngsim.utils;

import com.tenutz.storemngsim.domain.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtils {

    public static Optional<User> user() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal.toString().equals("anonymousUser"))
            return Optional.empty();

        return Optional.of((User) principal);
    }
}
