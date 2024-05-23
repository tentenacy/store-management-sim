package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.menu.OptionRepository;
import com.tenutz.storemngsim.web.api.dto.option.OptionsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionsResponse options(String strCd) {
        return new OptionsResponse(optionRepository.options(strCd).stream().map(option ->
                new OptionsResponse.Option(
                        option.getStrCd(),
                        option.getOptCd(),
                        option.getOptNm(),
                        option.getSellAmt(),
                        option.useYn()
                )).collect(Collectors.toList())
        );
    }
}
