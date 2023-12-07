package fourtalking.Nateam.global.exception.user;

import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class InvalidUserIdAndPasswordException extends BusinessException {

    public InvalidUserIdAndPasswordException() { super(ErrorCode.INVALID_USER_ID_AND_PASSWORD); }
}
