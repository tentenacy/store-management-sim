package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.user.Terms;
import com.tenutz.storemngsim.domain.user.TermsRepository;
import com.tenutz.storemngsim.web.api.dto.term.TermResponse;
import com.tenutz.storemngsim.web.api.dto.term.TermsResponse;
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

    public TermResponse termsDetails(String termsCd) {
        Terms foundTerms = termsRepository.findById(termsCd).orElseThrow(CEntityNotFoundException.CTermsNotFoundException::new);
        return new TermResponse(
                foundTerms.getTermsCd(),
                foundTerms.getTermsContent(),
                foundTerms.useYn(),
                foundTerms.getCreatedAt(),
                foundTerms.getCreatedBy(),
                foundTerms.getOpenDate(),
                foundTerms.getCloseDate()
        );
    }
}
