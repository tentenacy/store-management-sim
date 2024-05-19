package com.tenutz.storemngsim.web.exception.security;

import com.tenutz.storemngsim.web.api.dto.common.ErrorCode;

public class CTokenException extends CSecurityException {
    public CTokenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static class CAccessDeniedException extends CTokenException {

        public CAccessDeniedException() {
            super(ErrorCode.HANDLE_ACCESS_DENIED);
        }
    }

    public static class CRefreshTokenException extends CTokenException {

        public CRefreshTokenException() {
            super(ErrorCode.REFRESH_TOKEN_ERROR);
        }
    }

}
