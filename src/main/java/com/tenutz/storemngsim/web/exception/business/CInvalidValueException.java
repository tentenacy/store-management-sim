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

    public static class CAlreadyMainMenuCreatedException extends CInvalidValueException {
        public CAlreadyMainMenuCreatedException() {
            super(ErrorCode.ALREADY_MAIN_MENU_CREATED);
        }
    }

    public static class CAlreadyMainMenuDetailsCreatedException extends CInvalidValueException {
        public CAlreadyMainMenuDetailsCreatedException() {
            super(ErrorCode.ALREADY_MAIN_MENU_DETAILS_CREATED);
        }
    }

    public static class CAlreadyMainMenuMappedException extends CInvalidValueException {
        public CAlreadyMainMenuMappedException() {
            super(ErrorCode.ALREADY_MAIN_MENU_MAPPED);
        }
    }

    public static class CNonExistentOptionGroupMainMenuIncludedException extends CInvalidValueException {
        public CNonExistentOptionGroupMainMenuIncludedException() {
            super(ErrorCode.NON_EXISTENT_OPTION_GROUP_MAIN_MENU_INCLUDED);
        }
    }

    public static class CNonExistentMainMenuIncludedException extends CInvalidValueException {
        public CNonExistentMainMenuIncludedException() {
            super(ErrorCode.NON_EXISTENT_MAIN_MENU_INCLUDED);
        }
    }

    public static class CNonExistentCategoryIncludedException extends CInvalidValueException {
        public CNonExistentCategoryIncludedException() {
            super(ErrorCode.NON_EXISTENT_CATEGORY_INCLUDED);
        }
    }

    public static class CAlreadyOptionCreatedException extends CInvalidValueException {
        public CAlreadyOptionCreatedException() {
            super(ErrorCode.ALREADY_OPTION_CREATED);
        }
    }

    public static class CNonExistentOptionIncludedException extends CInvalidValueException {
        public CNonExistentOptionIncludedException() {
            super(ErrorCode.NON_EXISTENT_OPTION_INCLUDED);
        }
    }

    public static class CAlreadyOptionMappedException extends CInvalidValueException {
        public CAlreadyOptionMappedException() {
            super(ErrorCode.ALREADY_MAIN_MENU_MAPPED);
        }
    }

    public static class CNonExistentOptionGroupOptionIncludedException extends CInvalidValueException {
        public CNonExistentOptionGroupOptionIncludedException() {
            super(ErrorCode.NON_EXISTENT_OPTION_GROUP_MAIN_MENU_INCLUDED);
        }
    }

    public static class CAlreadyOptionGroupCreatedException extends CInvalidValueException {
        public CAlreadyOptionGroupCreatedException() {
            super(ErrorCode.ALREADY_OPTION_GROUP_CREATED);
        }
    }

    public static class CNonExistentOptionGroupIncludedException extends CInvalidValueException {
        public CNonExistentOptionGroupIncludedException() {
            super(ErrorCode.NON_EXISTENT_OPTION_GROUP_INCLUDED);
        }
    }

    public static class CReplyMaximumLevelExceededException extends CInvalidValueException {
        public CReplyMaximumLevelExceededException() {
            super(ErrorCode.REPLY_MAXIMUM_LEVEL_EXCEEDED);
        }
    }

    public static class CNotAReplyException extends CInvalidValueException {
        public CNotAReplyException() {
            super(ErrorCode.NOT_A_REPLY_ERROR);
        }
    }
}
