package com.tenutz.storemngsim.web.exception.business;

import com.tenutz.storemngsim.web.api.dto.common.ErrorCode;

public class CEntityNotFoundException extends CBusinessException {
    public CEntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static class CUserNotFoundException extends CEntityNotFoundException {
        public CUserNotFoundException() {
            super(ErrorCode.USER_NOT_FOUND);
        }
    }

    public static class CCategoryNotFoundException extends CEntityNotFoundException {
        public CCategoryNotFoundException() {
            super(ErrorCode.CATEGORY_NOT_FOUND);
        }
    }
}
