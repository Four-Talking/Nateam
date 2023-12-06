package fourtalking.Nateam.cartgame.dto;

import fourtalking.Nateam.cartgame.entity.CartGame;
import lombok.Builder;

public record CardGameRegisterDTO() {


  public record Request(Integer orderCount) {

    public CartGame toEntity() {
      return CartGame.builder()
          .orderCount(orderCount)
          .build();
    }
  }

  @Builder
  public record Response(Long gameId, String userName, String gameName,
                         Integer gamePrice, Integer orderCount) {

    public static Response of(CartGame cartGame, String gameName,
        String userName, Integer gamePrice) {

      return Response.builder()
          .gameId(cartGame.getGameId())
          .userName(gameName)
          .gameName(userName)
          .gamePrice(gamePrice)
          .orderCount(cartGame.getOrderCount())
          .build();
    }

  }

}
