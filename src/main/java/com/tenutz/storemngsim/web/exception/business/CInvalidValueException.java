package com.tenutz.storemngsim.web.exception.business;

import com.tenutz.storemngsim.web.api.dto.common.ErrorCode;

public class CInvalidValueException extends CBusinessException {
    public CInvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static class CAlreadySignedupException extends CInvalidValueException {
        public CAlreadySignedupException() {
            super(ErrorCode.ALREADY_SIGNEDUP);
        }
    }

    public static class CLoginFailedException extends CInvalidValueException {
        public CLoginFailedException() {
            super(ErrorCode.LOGIN_FAIL);
        }
    }

    public static class CAlreadyCategoryCreatedException extends CInvalidValueException {
        public CAlreadyCategoryCreatedException() {
            super(ErrorCode.ALREADY_CATEGORY_CREATED);
        }
    }
}
