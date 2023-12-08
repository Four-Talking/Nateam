package fourtalking.Nateam.order.dto;

import fourtalking.Nateam.game.entity.Game;
import lombok.Builder;

@Builder
public record OrderGameDTO(
    Long gameId,
    String gameName,
    int gamePrice,
    int orderCount) {

  public static OrderGameDTO of(Game game, int orderCount) {
    return OrderGameDTO.builder()
        .gameId(game.getGameId())
        .gameName(game.getGameName())
        .gamePrice(game.getGamePrice() * orderCount)
        .orderCount(orderCount)
        .build();

  }

}
