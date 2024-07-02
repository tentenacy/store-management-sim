package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.user.Help;
import com.tenutz.storemngsim.domain.user.HelpRepository;
import com.tenutz.storemngsim.utils.HttpReqRespUtils;
import com.tenutz.storemngsim.web.api.dto.common.CommonCondition;
import com.tenutz.storemngsim.web.api.dto.help.HelpCreateRequest;
import com.tenutz.storemngsim.web.api.dto.help.HelpsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HelpService {

    private final HelpRepository helpRepository;

    public HelpsResponse helps(CommonCondition commonCond) {
        return new HelpsResponse(
                helpRepository.helps(commonCond).stream().map(help ->
                        new HelpsResponse.Help(
                                help.getHelpSeq(),
                                help.getHelpTitle(),
                                help.getHelpContent(),
                                help.getImgName(),
                                help.getImgUrl(),
                                help.getCreatedAt(),
                                help.getCreatedBy(),
                                help.getCreatedIp(),
                                help.getUpdatedAt(),
                                help.getUpdatedBy(),
                                help.getUpdatedIp()
                        )
                ).collect(Collectors.toList())
        );
    }

    @Transactional
    public void create(HelpCreateRequest request) {
        helpRepository.save(
                Help.create(
                        request.getTitle(),
                        request.getContent(),
                        HttpReqRespUtils.getClientIpAddressIfServletRequestExist()
                )
        );
    }
}
