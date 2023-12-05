package fourtalking.Nateam.global.exception.jwt;

import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class UnsupportedJwtTokenException extends BusinessException {
    public UnsupportedJwtTokenException() {
        super(ErrorCode.UNSUPPORTED_JWT_TOKEN_EXCEPTION);
    }

    public UnsupportedJwtTokenException(Throwable cause) {
        super(ErrorCode.UNSUPPORTED_JWT_TOKEN_EXCEPTION, cause);
    }
}
