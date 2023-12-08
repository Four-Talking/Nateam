package fourtalking.Nateam.global.exception.user;

import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class DuplicatePasswordException extends BusinessException {

    public DuplicatePasswordException() {
        super(ErrorCode.DUPLICATE_PASSWORD_EXCEPTION);
    }
}
