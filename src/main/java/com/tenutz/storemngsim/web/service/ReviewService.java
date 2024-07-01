package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.customer.MenuReviewRepository;
import com.tenutz.storemngsim.domain.customer.StoreReview;
import com.tenutz.storemngsim.domain.customer.StoreReviewRepository;
import com.tenutz.storemngsim.utils.HttpReqRespUtils;
import com.tenutz.storemngsim.web.api.dto.common.CommonCondition;
import com.tenutz.storemngsim.web.api.dto.store.MenuReviewsResponse;
import com.tenutz.storemngsim.web.api.dto.store.StoreReviewReplyCreateRequest;
import com.tenutz.storemngsim.web.api.dto.store.StoreReviewsResponse;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException.CStoreReviewNotFoundException;
import com.tenutz.storemngsim.web.exception.business.CInvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final StoreReviewRepository storeReviewRepository;
    private final MenuReviewRepository menuReviewRepository;

    public Page<StoreReviewsResponse> storeReviews(String siteCd, String strCd, Pageable pageable, CommonCondition commonCond) {
        return storeReviewRepository.reviews(siteCd, strCd, pageable, commonCond);
    }

    public Page<MenuReviewsResponse> menuReviews(String siteCd, String strCd, Pageable pageable, CommonCondition commonCond) {
        return menuReviewRepository.reviews(siteCd, strCd, pageable, commonCond);
    }

    @Transactional
    public void createStoreReviewReply(Long reviewSeq, StoreReviewReplyCreateRequest request) {
        StoreReview foundStoreReview = storeReviewRepository.findById(reviewSeq).orElseThrow(CStoreReviewNotFoundException::new);
        if(!ObjectUtils.isEmpty(foundStoreReview.getNoLevel()) && foundStoreReview.getNoLevel() > 0) {
            throw new CInvalidValueException.CReplyMaximumLevelExceededException();
        }
        storeReviewRepository.save(
                StoreReview.create(
                        foundStoreReview.getDsSiteCd(),
                        foundStoreReview.getDsStrCd(),
                        request.getContent(),
                        foundStoreReview.getNoReviewSeq(),
                        foundStoreReview.getDsCateCd2(),
                        HttpReqRespUtils.getClientIpAddressIfServletRequestExist()
                )
        );
    }
}
