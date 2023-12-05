package fourtalking.Nateam.global.exception.user;

import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class UserNotFoundException extends BusinessException {

  public UserNotFoundException() {
    super(ErrorCode.NOT_FOUND_MEMBER_EXCEPTION);
  }
}
