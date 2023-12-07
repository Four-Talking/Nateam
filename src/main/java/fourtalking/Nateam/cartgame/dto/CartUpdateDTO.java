package fourtalking.Nateam.cartgame.dto;

import java.util.List;

public record CartUpdateDTO(List<CartGameDTO> cartGameDTOs,
                            int totalPrice) {

  public static CartUpdateDTO of(List<CartGameDTO> cartGameDTOs, int totalPrice) {
    return new CartUpdateDTO(cartGameDTOs, totalPrice);
  }

}
