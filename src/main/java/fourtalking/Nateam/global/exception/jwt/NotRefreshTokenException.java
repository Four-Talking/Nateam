package fourtalking.Nateam.global.exception.jwt;

import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class NotRefreshTokenException extends BusinessException {
    public NotRefreshTokenException() {
        super(ErrorCode.NOT_REFRESH_TOKEN_EXCEPTION);
    }
}
