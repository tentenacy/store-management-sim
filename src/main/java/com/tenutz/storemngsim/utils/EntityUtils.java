package com.tenutz.storemngsim.utils;

import com.tenutz.storemngsim.domain.user.User;
import com.tenutz.storemngsim.domain.user.UserRepository;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException.CUserNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

public class EntityUtils {
    public static User userThrowable(UserRepository userRepository) {
        return userRepository.findById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSeq())
                .orElseThrow(CUserNotFoundException::new);
    }
    public static User userThrowable(UserRepository userRepository, Integer seq) {
        return userRepository.findById(seq)
                .orElseThrow(CUserNotFoundException::new);
    }
}
