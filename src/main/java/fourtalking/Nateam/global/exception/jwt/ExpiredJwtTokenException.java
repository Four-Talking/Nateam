package fourtalking.Nateam.global.exception.jwt;

import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class ExpiredJwtTokenException extends BusinessException {

    public ExpiredJwtTokenException() {
        super(ErrorCode.EXPIRED_JWT_TOKEN_EXCEPTION);
    }

    public ExpiredJwtTokenException(Throwable cause) {
        super(ErrorCode.EXPIRED_JWT_TOKEN_EXCEPTION, cause);
    }
}
