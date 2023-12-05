package fourtalking.Nateam.global.exception.jwt;

import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class InvalidJwtTokenException extends BusinessException {

    public InvalidJwtTokenException() {
        super(ErrorCode.INVALID_JWT_TOKEN_EXCEPTION);
    }

    public InvalidJwtTokenException(Throwable cause) {
        super(ErrorCode.INVALID_JWT_TOKEN_EXCEPTION, cause);
    }
}
