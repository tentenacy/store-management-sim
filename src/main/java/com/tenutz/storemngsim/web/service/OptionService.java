package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.menu.Option;
import com.tenutz.storemngsim.domain.menu.OptionRepository;
import com.tenutz.storemngsim.domain.store.StoreMaster;
import com.tenutz.storemngsim.domain.store.StoreMasterRepository;
import com.tenutz.storemngsim.web.api.dto.option.OptionCreateRequest;
import com.tenutz.storemngsim.web.api.dto.option.OptionResponse;
import com.tenutz.storemngsim.web.api.dto.option.OptionsResponse;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException;
import com.tenutz.storemngsim.web.exception.business.CInvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OptionService {

    private final StoreMasterRepository storeMasterRepository;
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

    @Transactional
    public void createOption(String strCd, OptionCreateRequest request) {
        StoreMaster foundStoreMaster = storeMasterRepository.findAllByStrCd(strCd).stream().findAny().orElseThrow(CEntityNotFoundException.CStoreMasterNotFoundException::new);
        optionRepository.option(strCd, request.getOptionCode()).ifPresent(option -> {
            throw new CInvalidValueException.CAlreadyOptionCreatedException();
        });
        optionRepository.save(
                Option.create(
                        foundStoreMaster.getSiteCd(),
                        strCd,
                        request.getOptionCode(),
                        request.getOptionName(),
                        request.getPrice(),
                        !ObjectUtils.isEmpty(request.getDiscountedPrice()) ? request.getDiscountedPrice() : 0,
                        !ObjectUtils.isEmpty(request.getAdditionalPackagingPrice()) ? request.getAdditionalPackagingPrice() : 0,
                        request.getPackaging(),
                        request.getOutOfStock(),
                        request.getUse(),
                        request.getImageName(),
                        request.getOptionNameKor(),
                        request.getShowDateFrom(),
                        request.getShowDateTo(),
                        request.getShowTimeFrom(),
                        request.getShowTimeTo(),
                        request.getShowDayOfWeek(),
                        request.getEventDateFrom(),
                        request.getEventDateTo(),
                        request.getEventTimeFrom(),
                        request.getEventTimeTo(),
                        request.getEventDayOfWeek()
                )
        );
    }
}
