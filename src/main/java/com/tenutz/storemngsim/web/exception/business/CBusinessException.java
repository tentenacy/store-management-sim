package com.tenutz.storemngsim.web.exception.business;

import com.tenutz.storemngsim.web.api.common.dto.ErrorCode;
import lombok.Getter;

@Getter
public class CBusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public CBusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
