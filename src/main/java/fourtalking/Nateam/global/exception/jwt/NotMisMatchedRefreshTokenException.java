package fourtalking.Nateam.global.exception.jwt;

import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class NotMisMatchedRefreshTokenException extends BusinessException {
    public NotMisMatchedRefreshTokenException() {
        super(ErrorCode.NOT_MISMATCHED_REFRESH_TOKEN_EXCEPTION);
    }
}
