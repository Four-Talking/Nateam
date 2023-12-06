package fourtalking.Nateam.global.exception.review;

import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class ReviewNotFoundException extends BusinessException {
    public ReviewNotFoundException() {
        super(ErrorCode.NOT_FOUND_REVIEW_EXCEPTION);
    }
}
