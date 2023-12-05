package fourtalking.Nateam.global.exception.jwt;

import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class FailedAuthenticationException extends BusinessException {

    public FailedAuthenticationException() {
        super(ErrorCode.FAILED_AUTHENTICATION_EXCEPTION);
    }

    public FailedAuthenticationException(Throwable cause) {
        super(ErrorCode.FAILED_AUTHENTICATION_EXCEPTION, cause);
    }
}
