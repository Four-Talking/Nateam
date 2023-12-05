package fourtalking.Nateam.global.exception.jwt;

import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class NoJwtException extends BusinessException {

  public NoJwtException() {
    super(ErrorCode.NO_JWT_EXCEPTION);
  }
}
