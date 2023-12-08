package fourtalking.Nateam.global.exception.cartGame;

import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class CartInGameNotFountException extends BusinessException {

  public CartInGameNotFountException() {
    super(ErrorCode.NOT_FOUND_CART_IN_GAME_EXCEPTION);
  }
}
