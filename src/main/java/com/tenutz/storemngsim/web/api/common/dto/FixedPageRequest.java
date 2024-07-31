package com.tenutz.storemngsim.web.api.common.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class FixedPageRequest extends PageRequest {

    protected FixedPageRequest(Pageable pageable, long totalCount) {
        super(getPageNo(pageable, totalCount), pageable.getPageSize(), pageable.getSort());
    }

    private static int getPageNo(Pageable pageable, long totalCount) {
        int pageNo = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        long requestCount = pageNo * pageSize; // pageNo:10, pageSize:10 일 경우 requestCount=90

        if (totalCount > requestCount) { // 실제 건수가 요청한 페이지 번호보다 높을 경우
            return pageNo;
        }

        return (int) Math.ceil((double)totalCount/pageNo); // 실제 건수가 부족한 경우 요청 페이지 번호를 가장 높은 번호로 교체
    }
}
