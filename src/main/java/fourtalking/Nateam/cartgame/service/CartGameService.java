package fourtalking.Nateam.cartgame.service;

import fourtalking.Nateam.cartgame.dto.CartGameDTO;
import fourtalking.Nateam.cartgame.dto.CartUpdateDTO;
import fourtalking.Nateam.cartgame.dto.CartsGetDTO;
import fourtalking.Nateam.cartgame.entity.CartGame;
import fourtalking.Nateam.cartgame.repository.CartGameRepository;
import fourtalking.Nateam.game.entity.Game;
import fourtalking.Nateam.game.service.GameService;
import fourtalking.Nateam.global.exception.cartGame.CartInGameNotFountException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartGameService {

  private final CartGameRepository cartGameRepository;
  private final GameService gameService;

  // 장바구니 수정 서비스
  @Transactional
  public CartUpdateDTO updateCartGame(Long gameId, int orderCount, Long userId) {

    // 로그인중 유저 장바구니 목록 가져오기
    List<CartGame> cartList = findUserCart(userId);

    if (!existSameGameThenUpdateOrderCount(cartList, orderCount, gameId)) {
      addCartGame(cartList, orderCount, userId, gameId);
    }

    int totalPrice = 0;
    List<CartGameDTO> cartGameDTOs = new ArrayList<>();
    for (CartGame cartGame : cartList) {
      Game game = gameService.findById(cartGame.getGameId());

      CartGameDTO cartGameDTO = CartGameDTO.of(game, cartGame.getOrderCount());
      cartGameDTOs.add(cartGameDTO);
      totalPrice += cartGameDTO.eachGameTotalPrice();
    }

    return CartUpdateDTO.of(cartGameDTOs, totalPrice);
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

  // 장바구니 목록 조회 서비스
  @Transactional(readOnly = true)
  public CartsGetDTO getCartsGame(long userId) {

    // 로그인중 유저 장바구니 목록 가져오기
    List<CartGame> cartList = findUserCart(userId);

    int totalPrice = 0;
    List<CartGameDTO> cartGameDTOs = new ArrayList<>();

    for (CartGame cartGame : cartList) {
      Game game = gameService.findById(cartGame.getGameId());

      CartGameDTO cartGameDTO = CartGameDTO.of(game, cartGame.getOrderCount());
      cartGameDTOs.add(cartGameDTO);
      totalPrice += cartGameDTO.eachGameTotalPrice();
    }

    return CartsGetDTO.of(cartGameDTOs, totalPrice);
  }

  // 장바구니 게임 삭제 서비스
  public CartsGetDTO deleteCartGame(Long gameId, Long userId) {

    // 로그인중 유저 장바구니 목록 가져오기
    List<CartGame> cartList = findUserCart(userId);

    // 장바구니에 없는 게임 삭제 요청 하면 예외 처리
    if (!gameExistsInCartGame(cartList, gameId)) {
      throw new CartInGameNotFountException();
    }

    int totalPrice = 0;
    List<CartGameDTO> cartGameDTOs = remainingCartGame(cartList, gameId);

    for (CartGameDTO cartGameDTO : cartGameDTOs) {
      totalPrice += cartGameDTO.eachGameTotalPrice();
    }

    return CartsGetDTO.of(cartGameDTOs, totalPrice);
  }


  public List<CartGameDTO> remainingCartGame(List<CartGame> cartList, Long gameId) {

    List<CartGameDTO> cartGameDTOs = new ArrayList<>();

    for (CartGame cartGame : cartList) {
      Game game = gameService.findById(cartGame.getGameId());
      // 유저가 요청한 게임 삭제
      if (game.getGameId().equals(gameId)) {
        cartGameRepository.deleteById(cartGame.getCartGameId());
        continue;
      }
      CartGameDTO cartGameDTO = CartGameDTO.of(game, cartGame.getOrderCount());
      cartGameDTOs.add(cartGameDTO);
    }

    return cartGameDTOs;
  }

  public boolean gameExistsInCartGame(List<CartGame> cartList, Long gameId) {
    for (CartGame cartGame : cartList) {
      if (cartGame.getGameId().equals(gameId)) {
        return true;
      }
    }
    return false;
  }


  public List<CartGame> findUserCart(Long userId) {
    return cartGameRepository.findCartsByUserId(userId);
  }

}
