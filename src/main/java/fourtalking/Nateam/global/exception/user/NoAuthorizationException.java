package fourtalking.Nateam.global.exception.user;

import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class NoAuthorizationException extends BusinessException {

  public NoAuthorizationException() {
    super(ErrorCode.NO_AUTHORIZATION_EXCEPTION);
  }
}
