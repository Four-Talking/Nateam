package fourtalking.Nateam.cartgame.dto;

import java.util.List;

public record CartsGetDTO(List<CartGameDTO> cartGameDTOs, int totalPrice) {

  public static CartsGetDTO of(List<CartGameDTO> cartGameDTOs, int totalPrice) {
    return new CartsGetDTO(cartGameDTOs, totalPrice);
  }

}
