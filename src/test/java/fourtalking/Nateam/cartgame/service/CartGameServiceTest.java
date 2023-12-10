package fourtalking.Nateam.cartgame.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fourtalking.Nateam.cartgame.dto.CartGameDTO;
import fourtalking.Nateam.cartgame.dto.CartUpdateDTO;
import fourtalking.Nateam.cartgame.dto.CartsGetDTO;
import fourtalking.Nateam.cartgame.repository.CartGameRepository;
import fourtalking.Nateam.game.dto.GameRegisterDTO;
import fourtalking.Nateam.game.dto.GameRegisterDTO.Request;
import fourtalking.Nateam.game.service.GameService;
import fourtalking.Nateam.global.exception.cartGame.CartInGameNotFountException;
import fourtalking.Nateam.test.CommonTest;
import fourtalking.Nateam.test.GameTest;
import fourtalking.Nateam.user.dto.SignupDTO;
import fourtalking.Nateam.user.service.UserService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class CartGameServiceTest implements CommonTest, GameTest {

  @Autowired
  CartGameService cartGameService;

  @Autowired
  UserService userService;

  @Autowired
  GameService gameService;

  @Autowired
  CartGameRepository cartGameRepository;


  @BeforeEach
  public void setup() {
    SignupDTO signRequestDTO = new SignupDTO(TEST_USER_NAME, TEST_USER_PASSWORD);
    userService.signup(signRequestDTO);

    // 게임 등록
    GameRegisterDTO.Request testGameRequestDTO = new Request(TEST_GAME_NAME,
        TEST_GAME_INTRODUCTION, TEST_GAME_PRICE);
    GameRegisterDTO.Request OtherGameRequestDTO = new Request(OTRER_TEST_GAME_NAME,
        OTRER_TEST_GAME_INTRODUCTION, OTRER_TEST_GAME_PRICE);
    GameRegisterDTO.Request AnotherGameRequestDTO = new Request(ANOTRER_TEST_GAME_NAME,
        ANOTRER_TEST_GAME_INTRODUCTION, ANOTRER_TEST_GAME_PRICE);

    gameService.registerGame(testGameRequestDTO, TEST_USER_ID);
    gameService.registerGame(OtherGameRequestDTO, TEST_USER_ID);
    gameService.registerGame(AnotherGameRequestDTO, TEST_USER_ID);


  }

  @Nested
  @DisplayName("장바구니 테스트 한번에 하기")
  class AllCartGameTest {

    @Test
    @DisplayName("장바구니 수정")
    void updateCartGame() {
      // given
      int orderCount = 5;

      // when
      CartUpdateDTO cartUpdateDTO = cartGameService.updateCartGame(TEST_GAME_ID, orderCount,
          TEST_USER_ID);

      // then
      List<CartGameDTO> cartGameDTOs = cartUpdateDTO.cartGameDTOs();

      assertEquals(cartGameDTOs.size(), 1);
      assertEquals(cartGameDTOs.get(0).gameName(), TEST_GAME_NAME);
      assertEquals(cartUpdateDTO.totalPrice(), TEST_GAME_PRICE * orderCount);

    }

    @Test
    @DisplayName("장바구니 조회")
    void getCartsGame() {
      // given
      int orderCount = 1;
      int otherOrderCount = 2;
      int anotherOrderCount = 3;
      int totalPrice = (TEST_GAME_PRICE * orderCount) + (OTRER_TEST_GAME_PRICE * otherOrderCount)
          + (ANOTRER_TEST_GAME_PRICE * anotherOrderCount);

      cartGameService.updateCartGame(TEST_GAME_ID, orderCount, TEST_USER_ID);
      cartGameService.updateCartGame(OTRER_TEST_GAME_ID, otherOrderCount, TEST_USER_ID);
      cartGameService.updateCartGame(ANOTRER_TEST_GAME_ID, anotherOrderCount, TEST_USER_ID);

      // when
      CartsGetDTO cartsGetDTO = cartGameService.getCartsGame(TEST_USER_ID);

      // then
      assertEquals(cartsGetDTO.cartGameDTOs().size(), 3); // 해당 유저 장바구니안 게임 3개인지 확인
      assertEquals(cartsGetDTO.totalPrice(), totalPrice);

    }

    @Test
    @DisplayName("장바구니 삭제")
    void deleteCartGame() {
      // given
      int orderCount = 1;
      int otherOrderCount = 2;
      int totalPrice = OTRER_TEST_GAME_PRICE * otherOrderCount;

      cartGameService.updateCartGame(TEST_GAME_ID, orderCount, TEST_USER_ID);
      cartGameService.updateCartGame(OTRER_TEST_GAME_ID, otherOrderCount, TEST_USER_ID);

      // when
      CartsGetDTO cartsGetDTO = cartGameService.deleteCartGame(TEST_GAME_ID, TEST_USER_ID);

      // then
      assertEquals(cartsGetDTO.totalPrice(), totalPrice);  // 삭제하고 남은 목록 가격 체크

    }

    @Test
    @DisplayName("장바구니에 없는 게임 삭제 요청시 예외 발생")
    void deleteCartGameByWrongId() {
      // given
      int orderCount = 1;

      cartGameService.updateCartGame(TEST_GAME_ID, orderCount, TEST_USER_ID);

      // when , then
      assertThrows(CartInGameNotFountException.class,
        () -> cartGameService.deleteCartGame(WRONG_TEST_GAME_ID, TEST_USER_ID));
    }


  }


}
