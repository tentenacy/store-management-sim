package com.tenutz.storemngsim.web.exception.business;

import com.tenutz.storemngsim.web.api.common.dto.ErrorCode;

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

    public static class COptionGroupNotFoundException extends CEntityNotFoundException {
        public COptionGroupNotFoundException() {
            super(ErrorCode.OPTION_GROUP_NOT_FOUND);
        }
    }

    public static class CMenuImageNotFoundException extends CEntityNotFoundException {
        public CMenuImageNotFoundException() {
            super(ErrorCode.MENU_IMAGE_NOT_FOUND);
        }
    }

    public static class CStoreReviewNotFoundException extends CEntityNotFoundException {
        public CStoreReviewNotFoundException() {
            super(ErrorCode.STORE_REVIEW_NOT_FOUND);
        }
    }

    public static class CMenuReviewNotFoundException extends CEntityNotFoundException {
        public CMenuReviewNotFoundException() {
            super(ErrorCode.MENU_REVIEW_NOT_FOUND);
        }
    }

    public static class CManagerNotFoundException extends CEntityNotFoundException {
        public CManagerNotFoundException() {
            super(ErrorCode.MANAGER_NOT_FOUND);
        }
    }

    public static class CTermsNotFoundException extends CEntityNotFoundException {
        public CTermsNotFoundException() {
            super(ErrorCode.TERMS_NOT_FOUND);
        }
    }

    public static class CSalesMasterNotFoundException extends CEntityNotFoundException {
        public CSalesMasterNotFoundException() {
            super(ErrorCode.SALES_MASTER_NOT_FOUND);
        }
    }

    public static class CSalesPaymentNotFoundException extends CEntityNotFoundException {
        public CSalesPaymentNotFoundException() {
            super(ErrorCode.SALES_PAYMENT_NOT_FOUND);
        }
    }

    public static class CSalesDetailsNotFoundException extends CEntityNotFoundException {
        public CSalesDetailsNotFoundException() {
            super(ErrorCode.SALES_DETAILS_NOT_FOUND);
        }
    }
}
