package fourtalking.Nateam.cartgame.dto;

import fourtalking.Nateam.cartgame.entity.CartGame;
import fourtalking.Nateam.game.entity.Game;
import fourtalking.Nateam.user.entity.User;
import lombok.Builder;

public class CartGameRegisterDTO {

  public record CartRequest(Integer orderCount) {

    public CartGame toEntity(User user, Game game) {
      return CartGame.builder()
          .orderCount(orderCount)
          .user(user)
          .game(game)
          .build();
    }
  }

  @Builder
  public record CartResponse(Long gameId, String userName, String gameName,
                         Integer gamePrice, Integer orderCount) {

    public static CartGameRegisterDTO.CartResponse of(CartGame cartGame, String gameName,
        String userName, Integer gamePrice) {

      return CartGameRegisterDTO.CartResponse.builder()
          .gameId(cartGame.getGameId())
          .userName(gameName)
          .gameName(userName)
          .gamePrice(gamePrice)
          .orderCount(cartGame.getOrderCount())
          .build();
    }

  }
}
