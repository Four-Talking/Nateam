package fourtalking.Nateam.cartgame.dto;

import fourtalking.Nateam.cartgame.entity.CartGame;
import fourtalking.Nateam.game.entity.Game;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;

public class CartGameRegisterDTO {

  public record CartRequest(
      @Min(1) @Max(99)
      Integer orderCount) {

    public CartGame toEntity(Long gameId, Long userId) {
      return CartGame.builder()
          .orderCount(orderCount)
          .userId(userId)
          .gameId(gameId)
          .build();
    }
  }

  @Builder
  public record CartResponse(Long gameId, String userName, String gameName,
                             Integer gamePrice, Integer orderCount) {

    public static CartGameRegisterDTO.CartResponse of(Game game, Integer orderCount,String username) {

      return CartGameRegisterDTO.CartResponse.builder()
          .gameId(game.getGameId())
          .userName(username)
          .gameName(game.getGameName())
          .gamePrice(game.getGamePrice())
          .orderCount(orderCount)
          .build();
    }

  }
}
