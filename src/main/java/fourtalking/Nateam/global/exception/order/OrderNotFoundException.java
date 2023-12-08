package fourtalking.Nateam.global.exception.order;


import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class OrderNotFoundException extends BusinessException {
  public OrderNotFoundException() {
    super(ErrorCode.NOT_FOUND_ORDER_EXCEPTION);
  }
}
