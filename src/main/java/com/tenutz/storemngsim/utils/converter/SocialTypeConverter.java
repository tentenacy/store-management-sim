package com.tenutz.storemngsim.utils.converter;

import com.tenutz.storemngsim.utils.enums.SocialType;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
class SocialTypeConverter implements Converter<String, SocialType> {
    @Override
    public SocialType convert(String s) {
        return SocialType.valueOf(s.toUpperCase());
    }
}