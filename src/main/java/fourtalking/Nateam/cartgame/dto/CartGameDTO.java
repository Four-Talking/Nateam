package fourtalking.Nateam.cartgame.dto;

import fourtalking.Nateam.game.entity.Game;
import lombok.Builder;

@Builder
public record CartGameDTO(
    Long gameId,
    String gameName,
    int gamePrice,
    Integer orderCount,
    Integer eachGameTotalPrice) {

  public static CartGameDTO of(Game game, int orderCount){
    return CartGameDTO.builder()
        .gameId(game.getGameId())
        .gameName(game.getGameName())
        .gamePrice(game.getGamePrice())
        .orderCount(orderCount)
        .eachGameTotalPrice(game.getGamePrice() * orderCount)
        .build();
  }
}
