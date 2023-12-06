package fourtalking.Nateam.cartgame.service;

import fourtalking.Nateam.cartgame.dto.CartGameRegisterDTO.CartRequest;
import fourtalking.Nateam.cartgame.dto.CartGameRegisterDTO.CartResponse;
import fourtalking.Nateam.cartgame.entity.CartGame;
import fourtalking.Nateam.cartgame.repository.CartGameRepository;
import fourtalking.Nateam.game.entity.Game;
import fourtalking.Nateam.game.repositroy.GameRepository;
import fourtalking.Nateam.global.exception.game.GameNotFoundException;
import fourtalking.Nateam.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartGameService {

  private final CartGameRepository cartGameRepository;
  private final GameRepository gameRepository;

  @Transactional
  public List<CartResponse> updateCartGame(Long gameId, CartRequest requestDto, User user) {
    // 로그인중 유저 장바구니 목록 가져오기
    List<CartGame> cartList = findUserCart(user.getUserId());

    // 장바구니 아예 비어 있거나,해당게임 장바구에 새로 추가하기 위해
    Game game = findById(gameId);

    // 장바구니에 게임이 한개도 없을때
    if (cartList.isEmpty()) {
      cartList.add(cartGameRepository.save(requestDto.toEntity(user, game)));
    }
    // 요청한 게임이 장바구니에 있을때
    for (CartGame cart : cartList) {
      if (cart.getGameId().equals(gameId)) {

      }
    }
    // 요청한 게임이 장바구니에 없을때

  }


  public Game findById(Long gameId) {
    return gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
  }

  public List<CartGame> findUserCart(Long userId) {
    return cartGameRepository.findCartsByUserId(userId);

  }

}
