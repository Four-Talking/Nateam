package fourtalking.Nateam.global.exception.jwt;

import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class InvalidJwtSignatureException extends BusinessException {

    public InvalidJwtSignatureException() {
        super(ErrorCode.INVALID_JWT_SIGNATURE_EXCEPTION);
    }

    public InvalidJwtSignatureException(Throwable cause) {
        super(ErrorCode.INVALID_JWT_SIGNATURE_EXCEPTION, cause);
    }
}
