package fourtalking.Nateam.cartgame.service;

import fourtalking.Nateam.cartgame.dto.CartGameRegisterDTO;
import fourtalking.Nateam.cartgame.dto.CartGameRegisterDTO.CartResponse;
import fourtalking.Nateam.cartgame.entity.CartGame;
import fourtalking.Nateam.cartgame.repository.CartGameRepository;
import fourtalking.Nateam.game.entity.Game;
import fourtalking.Nateam.game.service.GameService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartGameService {

  private final CartGameRepository cartGameRepository;
  private final GameService gameService;

  @Transactional
  public List<CartResponse> updateCartGame(Long gameId, int orderCount, Long userId) {

    // 로그인중 유저 장바구니 목록 가져오기
    List<CartGame> cartList = findUserCart(userId);

    if (!existSameGameThenUpdateOrderCount(cartList, orderCount, gameId)) {
      addCartGame(cartList, orderCount, userId, gameId);
    }

    return cartList.stream().map(cartGame ->
            CartGameRegisterDTO.CartResponse.of(
                findById(cartGame.getGameId()), cartGame.getOrderCount(), "seok"))
        .toList();
  }

  private boolean existSameGameThenUpdateOrderCount(List<CartGame> cartList, int orderCount,
      Long gameId) {

    for (CartGame cart : cartList) {
      // 요청한 게임이 장바구니에 있을때
      if (cart.getGameId().equals(gameId)) {
        cart.updateOrderCount(orderCount);
        return true;
      }
    }
    return false;
  }

  private void addCartGame(List<CartGame> cartList, int orderCount, Long userId, Long gameId) {

    CartGame cartGame = CartGame.createCartGame(orderCount, userId, gameId);
    CartGame savedCartGame = cartGameRepository.save(cartGame);
    cartList.add(savedCartGame);
  }


  public Game findById(Long gameId) {
    return gameService.findById(gameId);
  }


  public List<CartGame> findUserCart(Long userId) {
    return cartGameRepository.findCartsByUserId(userId);
  }

}
