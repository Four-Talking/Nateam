package fourtalking.Nateam.game.dto;

import fourtalking.Nateam.game.entity.Game;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record GameGetDTO(
        Long gameId,
        String gameName,
        String gameIntroduction,
        int gamePrice,
        double gameReviewRank,
        String userName,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
) {

    public static GameGetDTO of(Game game, double gameReviewRank, String userName) {
        return GameGetDTO.builder()
                .gameId(game.getGameId())
                .gameName(game.getGameName())
                .gameIntroduction(game.getGameIntroduction())
                .gamePrice(game.getGamePrice())
                .gameReviewRank(gameReviewRank)
                .userName(userName)
                .createdDate(game.getCreatedTime())
                .lastModifiedDate(game.getLastModifiedTime())
                .build();
    }

}
