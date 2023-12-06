package fourtalking.Nateam.global.exception.game;

import fourtalking.Nateam.global.exception.common.BusinessException;
import fourtalking.Nateam.global.exception.common.ErrorCode;

public class GameNotFoundException extends BusinessException {

    public GameNotFoundException() {
        super(ErrorCode.NOT_FOUND_GAME_EXCEPTION);
    }
}
