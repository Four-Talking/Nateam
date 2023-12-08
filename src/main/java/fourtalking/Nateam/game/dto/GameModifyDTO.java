package fourtalking.Nateam.game.dto;

import fourtalking.Nateam.game.entity.Game;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Builder;

public class GameModifyDTO {

    public record Request(
            @NotBlank @Size String gameName,
            @NotBlank @Size String gameIntroduction,
            @Positive int gamePrice) {

    }

    @Builder
    public record Response(String gameName, String gameIntroduction, int gamePrice,
                           double gameReviewRank, String userName, LocalDateTime createdDate
            , LocalDateTime lastModifiedDate) {

        public static GameModifyDTO.Response of(Game game, double gameReviewRank, String userName) {
            return Response.builder()
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

}
