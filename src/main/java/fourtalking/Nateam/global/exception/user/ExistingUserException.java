package fourtalking.Nateam.global.exception.user;

import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class ExistingUserException extends BusinessException {

    public ExistingUserException() {
        super(ErrorCode.ALREADY_EXIST_USER_NAME_EXCEPTION);
    }
}
