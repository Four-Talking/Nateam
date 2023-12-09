package fourtalking.Nateam.game.dto;

import fourtalking.Nateam.game.entity.Game;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Builder;

public class GameRegisterDTO {

    public record Request(
            @NotBlank @Size String gameName,
            @NotBlank @Size String gameIntroduction,
            @Positive int gamePrice) {
        public Game toEntity(Long userId) {
            return Game.builder()
                    .gameName(gameName)
                    .gameIntroduction(gameIntroduction)
                    .gamePrice(gamePrice)
                    .userId(userId)
                    .build();
        }
    }

    @Builder
    public record Response(Long gameId, String gameName, String gameIntroduction, int gamePrice,
                           double gameReviewRank, String userName,LocalDateTime createdDate) {

        public static Response of(Game game, double gameReviewRank, String userName) {
            return Response.builder()
                    .gameId(game.getGameId())
                    .gameName(game.getGameName())
                    .gameIntroduction(game.getGameIntroduction())
                    .gamePrice(game.getGamePrice())
                    .gameReviewRank(gameReviewRank)
                    .userName(userName)
                    .createdDate(game.getCreatedTime())
                    .build();
        }

    }
}
