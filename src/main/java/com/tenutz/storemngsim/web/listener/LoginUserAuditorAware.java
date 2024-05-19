package com.tenutz.storemngsim.web.listener;

import com.tenutz.storemngsim.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LoginUserAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal.toString().equals("anonymousUser"))
            return Optional.empty();

        return Optional.ofNullable(((User) principal).getUserId());
    }
}
