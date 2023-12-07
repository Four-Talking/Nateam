package fourtalking.Nateam.global.exception.user;

import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class WrongPasswordException extends BusinessException {

    public WrongPasswordException() {
        super(ErrorCode.WRONG_PASSWORD_EXCEPTION);
    }
}
