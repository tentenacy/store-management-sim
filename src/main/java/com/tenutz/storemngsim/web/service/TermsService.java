package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.user.TermsRepository;
import com.tenutz.storemngsim.web.api.dto.term.TermsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TermsService {

    private final TermsRepository termsRepository;


    public TermsResponse terms() {
        return new TermsResponse(
                termsRepository.terms().stream().map(terms -> new TermsResponse.Term(
                        terms.getTermsCd(),
                        terms.getTermsContent(),
                        terms.useYn(),
                        terms.getCreatedAt(),
                        terms.getCreatedBy(),
                        terms.getOpenDate(),
                        terms.getCloseDate()
                )).collect(Collectors.toList())
        );
    }
}
