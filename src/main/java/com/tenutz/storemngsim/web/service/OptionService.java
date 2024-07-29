package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.menu.*;
import com.tenutz.storemngsim.domain.store.StoreMaster;
import com.tenutz.storemngsim.domain.store.StoreMasterRepository;
import com.tenutz.storemngsim.web.api.common.dto.CommonCondition;
import com.tenutz.storemngsim.web.api.storemngsim.dto.common.OptionGroupsMappedByRequest;
import com.tenutz.storemngsim.web.api.storemngsim.dto.optiongroup.option.*;
import com.tenutz.storemngsim.web.client.common.UploadClient;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException;
import com.tenutz.storemngsim.web.exception.business.CInvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OptionService {

    private final StoreMasterRepository storeMasterRepository;
    private final OptionRepository optionRepository;
    private final OptionGroupOptionRepository optionGroupOptionRepository;
    private final MenuImageRepository menuImageRepository;
    private final UploadClient s3Client;

    public OptionsResponse options(String siteCd, String strCd, CommonCondition commonCond) {
        return new OptionsResponse(optionRepository.options(siteCd, strCd, commonCond).stream().map(option ->
                new OptionsResponse.Option(
                        option.getStrCd(),
                        option.getOptCd(),
                        option.getOptNm(),
                        menuImageRepository.findBySiteCdAndStrCdAndFileNm(siteCd, strCd, option.getImgNm())
                                .map(image -> s3Client.getFileUrl(image.getFilePath().substring(image.getFilePath().indexOf("FILE_MANAGER"))) + "/" + image.getFileNm())
                                .orElse(null),
                        option.getSellAmt(),
                        option.useYn()
                )).collect(Collectors.toList())
        );
    }

    public OptionResponse option(String siteCd, String strCd, String optionCd) {
        Option foundOption = optionRepository.option(siteCd, strCd, optionCd).orElseThrow(CEntityNotFoundException.COptionNotFoundException::new);
        return new OptionResponse(
                foundOption.getStrCd(),
                foundOption.getOptCd(),
                foundOption.getOptNm(),
                foundOption.getSellAmt(),
                foundOption.useYn(),
                foundOption.getImgNm(),
                menuImageRepository.findBySiteCdAndStrCdAndFileNm(siteCd, strCd, foundOption.getImgNm())
                        .map(image -> s3Client.getFileUrl(image.getFilePath().substring(image.getFilePath().indexOf("FILE_MANAGER"))) + "/" + image.getFileNm())
                        .orElse(null)
        );
    }

    @Transactional
    public void createOption(String siteCd, String strCd, OptionCreateRequest request) {

        StoreMaster foundStoreMaster = storeMasterRepository.findAllByStrCd(strCd).stream().findAny().orElseThrow(CEntityNotFoundException.CStoreMasterNotFoundException::new);

        Optional<Option> optionOptional = optionRepository.option(siteCd, strCd, request.getOptionCode());
        if(optionOptional.isPresent() && optionOptional.get().getUseYn().equals("D")) {
            optionOptional.get().update(
                    request.getOptionName(),
                    request.getPrice(),
                    request.getUse(),
                    request.getImageName()
            );
        } else if(optionOptional.isPresent()) {
            throw new CInvalidValueException.CAlreadyOptionCreatedException();
        } else {
            optionRepository.save(
                    Option.create(
                            foundStoreMaster.getSiteCd(),
                            strCd,
                            request.getOptionCode(),
                            request.getOptionName(),
                            request.getPrice(),
                            request.getUse(),
                            request.getImageName()
                    )
            );
        }
    }

    @Transactional
    public void updateOption(String siteCd, String strCd, String optionCd, OptionUpdateRequest request) {
        Option foundOption = optionRepository.option(siteCd, strCd, optionCd).orElseThrow(CEntityNotFoundException.COptionNotFoundException::new);
        foundOption.update(
                request.getOptionName(),
                request.getPrice(),
                request.getUse(),
                request.getImageName()
        );
    }

    @Transactional
    public void deleteOption(String siteCd, String strCd, String optionCd) {
        Option foundOption = optionRepository.option(siteCd, strCd, optionCd).orElseThrow(CEntityNotFoundException.COptionNotFoundException::new);
        foundOption.delete();
    }

    @Transactional
    public void deleteOptions(String siteCd, String strCd, OptionsDeleteRequest request) {
        List<Option> foundOptions = optionRepository.options(siteCd, strCd, request.getOptionCodes(), "X");
        if(request.getOptionCodes().size() != foundOptions.size()) {
            throw new CInvalidValueException.CNonExistentOptionIncludedException();
        }
        foundOptions.forEach(Option::delete);
    }

    @Transactional
    public void mapToOptionGroups(String siteCd, String strCd, String optionCd, OptionGroupsMappedByRequest request) {
        List<OptionGroupOption> foundOptionGroupOptions = optionGroupOptionRepository.optionGroupOptions(siteCd, strCd, optionCd, request.getOptionGroupCodes(), "D");
        List<String> optionGroupCodes = foundOptionGroupOptions.stream().map(OptionGroupOption::getOptGrpCd).collect(Collectors.toList());
        request.getOptionGroupCodes().forEach(code -> {
            if(optionGroupCodes.contains(code)) {
                foundOptionGroupOptions.stream()
                        .filter(optionGroupOption -> optionGroupOption.getOptGrpCd().equals(code))
                        .findAny().ifPresent(OptionGroupOption::use);
            } else {
                optionGroupOptionRepository.save(
                        OptionGroupOption.create(
                                siteCd,
                                strCd,
                                optionCd,
                                code,
                                latestPriority(optionGroupOptionRepository.latestPriorities(siteCd, strCd, optionCd)) + 1
                        )
                );
            }
        });
    }

    private int latestPriority(List<Integer> latestPriorities) {
        return latestPriorities.isEmpty() ? 0 : (ObjectUtils.isEmpty(latestPriorities.get(0)) ? 0 : latestPriorities.get(0));
    }
}
