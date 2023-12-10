package fourtalking.Nateam.global.exception.order;

import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class OrderEmptyCartException  extends BusinessException {
  public OrderEmptyCartException(){
    super(ErrorCode.EMPTY_CART_EXCEPTION);
  }

}
