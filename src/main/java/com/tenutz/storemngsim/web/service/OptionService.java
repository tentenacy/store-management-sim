package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.menu.Option;
import com.tenutz.storemngsim.domain.menu.OptionRepository;
import com.tenutz.storemngsim.web.api.dto.option.OptionResponse;
import com.tenutz.storemngsim.web.api.dto.option.OptionsResponse;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException;
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

    public OptionResponse option(String strCd, String optionCd) {
        Option foundOption = optionRepository.option(strCd, optionCd).orElseThrow(CEntityNotFoundException.COptionNotFoundException::new);
        return new OptionResponse(
                foundOption.getStrCd(),
                foundOption.getOptCd(),
                foundOption.getOptNm(),
                foundOption.getSellAmt(),
                foundOption.getSaleAmt(),
                foundOption.getTakeoutAmt(),
                foundOption.getTakeoutGbcd(),
                foundOption.soldOutYn(),
                foundOption.useYn(),
                foundOption.getImgNm(),
                foundOption.getOptKorNm(),
                foundOption.getShowSdate(),
                foundOption.getShowEdate(),
                foundOption.getShowStime(),
                foundOption.getShowEtime(),
                foundOption.getShowWeekday(),
                foundOption.getEvtSdate(),
                foundOption.getEvtEdate(),
                foundOption.getEvtStime(),
                foundOption.getEvtEtime(),
                foundOption.getEvtWeekday()
        );
    }
}
