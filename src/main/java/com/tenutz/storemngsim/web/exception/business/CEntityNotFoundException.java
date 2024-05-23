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

    public static class CStoreMasterNotFoundException extends CEntityNotFoundException {
        public CStoreMasterNotFoundException() {
            super(ErrorCode.STORE_MASTER_NOT_FOUND);
        }
    }

    public static class CMainMenuNotFoundException extends CEntityNotFoundException {
        public CMainMenuNotFoundException() {
            super(ErrorCode.MAIN_MENU_NOT_FOUND);
        }
    }

    public static class CMainMenuDetailsNotFoundException extends CEntityNotFoundException {
        public CMainMenuDetailsNotFoundException() {
            super(ErrorCode.MAIN_MENU_DETAILS_NOT_FOUND);
        }
    }

    public static class COptionGroupMainMenuNotFoundException extends CEntityNotFoundException {
        public COptionGroupMainMenuNotFoundException() {
            super(ErrorCode.OPTION_GROUP_MAIN_MENU_NOT_FOUND);
        }
    }

    public static class COptionNotFoundException extends CEntityNotFoundException {
        public COptionNotFoundException() {
            super(ErrorCode.OPTION_NOT_FOUND);
        }
    }
}
