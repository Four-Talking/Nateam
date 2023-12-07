package fourtalking.Nateam.global.exception.review;

import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class InconsistencyUserIdException extends BusinessException {

    public InconsistencyUserIdException() {
        super(ErrorCode.INCONSISTENCY_USERID_EXCEPTION);
    }
}
