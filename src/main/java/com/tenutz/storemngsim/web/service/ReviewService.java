package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.domain.customer.MenuReviewRepository;
import com.tenutz.storemngsim.domain.customer.StoreReview;
import com.tenutz.storemngsim.domain.customer.StoreReviewRepository;
import com.tenutz.storemngsim.utils.HttpReqRespUtils;
import com.tenutz.storemngsim.web.api.dto.common.CommonCondition;
import com.tenutz.storemngsim.web.api.dto.store.MenuReviewsResponse;
import com.tenutz.storemngsim.web.api.dto.store.StoreReviewReplyCreateRequest;
import com.tenutz.storemngsim.web.api.dto.store.StoreReviewReplyUpdateRequest;
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
import org.springframework.util.StringUtils;

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
        StoreReview foundStoreReview = storeReviewRepository.storeReview(reviewSeq).orElseThrow(CStoreReviewNotFoundException::new);
        validateReplyCreation(foundStoreReview);
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

    @Transactional
    public void updateStoreReviewReply(Long replySeq, String siteCd, String strCd, StoreReviewReplyUpdateRequest request) {
        StoreReview foundStoreReply = storeReviewRepository.storeReview(replySeq).orElseThrow(CStoreReviewNotFoundException::new);
        validateReply(foundStoreReply, siteCd, strCd);
        foundStoreReply.update(request.getContent(), HttpReqRespUtils.getClientIpAddressIfServletRequestExist());
        storeReviewRepository.save(foundStoreReply);
    }

    @Transactional
    public void deleteStoreReviewReply(Long replySeq, String siteCd, String strCd) {
        StoreReview foundStoreReply = storeReviewRepository.storeReview(replySeq).orElseThrow(CStoreReviewNotFoundException::new);
        validateReply(foundStoreReply, siteCd, strCd);
        foundStoreReply.delete();
        storeReviewRepository.save(foundStoreReply);
    }

    private void validateReplyCreation(StoreReview foundStoreReview) {
        validateReplyLevel(foundStoreReview);
        validateReplyCount(foundStoreReview);
    }

    private void validateReplyCount(StoreReview foundStoreReview) {
        if(storeReviewRepository.countByNoHighReviewSeq(foundStoreReview.getNoReviewSeq()) > 0) {
            throw new CInvalidValueException.CReplyMaximumCountExceededException();
        }
    }

    private void validateReplyLevel(StoreReview foundStoreReview) {
        if(!ObjectUtils.isEmpty(foundStoreReview.getNoLevel()) && foundStoreReview.getNoLevel() > 0) {
            throw new CInvalidValueException.CReplyMaximumLevelExceededException();
        }
    }

    private void validateReply(StoreReview foundStoreReply, String siteCd, String strCd) {
        if(ObjectUtils.isEmpty(foundStoreReply.getNoLevel()) || foundStoreReply.getNoLevel() == 0) {
            throw new CInvalidValueException.CNotAReplyException();
        }
        if(!foundStoreReply.getDsSiteCd().equals(siteCd) || !foundStoreReply.getDsStrCd().equals(strCd)) {
            throw new CInvalidValueException.CNotUserOwnReplyException();
        }
    }
}
