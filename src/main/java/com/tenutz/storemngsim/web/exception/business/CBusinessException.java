package com.tenutz.storemngsim.web.exception.business;

import com.tenutz.storemngsim.web.api.dto.common.ErrorCode;
import lombok.Getter;

@Getter
public class CBusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public CBusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
