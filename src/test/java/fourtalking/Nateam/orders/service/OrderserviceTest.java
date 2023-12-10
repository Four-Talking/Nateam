package fourtalking.Nateam.orders.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fourtalking.Nateam.cartgame.service.CartGameService;
import fourtalking.Nateam.game.dto.GameRegisterDTO;
import fourtalking.Nateam.game.dto.GameRegisterDTO.Request;
import fourtalking.Nateam.game.service.GameService;
import fourtalking.Nateam.global.exception.order.OrderEmptyCartException;
import fourtalking.Nateam.global.exception.order.OrderNotFoundException;
import fourtalking.Nateam.global.exception.review.InconsistencyUserIdException;
import fourtalking.Nateam.order.dto.OrderGetDTO;
import fourtalking.Nateam.order.dto.OrderRegisterDTO;
import fourtalking.Nateam.order.repository.OrderRepository;
import fourtalking.Nateam.order.service.OrderService;
import fourtalking.Nateam.orderGame.service.OrderGameService;
import fourtalking.Nateam.test.CommonTest;
import fourtalking.Nateam.test.GameTest;
import fourtalking.Nateam.user.dto.SignupDTO;
import fourtalking.Nateam.user.service.UserService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class OrderserviceTest implements CommonTest, GameTest {

  @Autowired
  OrderService orderService;

  @Autowired
  OrderGameService orderGameService;

  @Autowired
  CartGameService cartGameService;

  @Autowired
  UserService userService;

  @Autowired
  GameService gameService;

  @Autowired
  OrderRepository orderRepository;

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

  @Test
  @DisplayName("주문하기 - 장바구니에 있는 게임 주문")
  void registerOrder() {
    // given
    int orderCount = 1;
    int otherOrderCount = 2;
    int totalPrice = (TEST_GAME_PRICE * orderCount) + (OTRER_TEST_GAME_PRICE * otherOrderCount);

    cartGameService.updateCartGame(TEST_GAME_ID, orderCount, TEST_USER_ID);
    cartGameService.updateCartGame(OTRER_TEST_GAME_ID, otherOrderCount, TEST_USER_ID);

    // when
    OrderRegisterDTO orderRegisterDTO = orderService.registerOrder(TEST_USER);

    // then
    assertEquals(orderRegisterDTO.totalPrice(), totalPrice);
    assertEquals(orderRegisterDTO.userName(), TEST_USER_NAME);
    assertEquals(orderRegisterDTO.orderGameList().size(), 2);
  }

  @Test
  @DisplayName("주문하기 - 장바구니 비어 있는데 주문하기 요청시 예외 발생")
  void registerOrderCartGameEmpty() {
    // gevin

    // when,then
    Exception exception = assertThrows(OrderEmptyCartException.class,
        () -> orderService.registerOrder(TEST_USER));
    assertEquals("장바구니가 비어 있습니다", exception.getMessage());
  }

  @Test
  @DisplayName("주문 조회 - 주문 ID로 단일 주문 조회")
  void getOrder() {
    // given
    Long orderId = 1L;
    int orderCount = 2;
    int totalPrice = (TEST_GAME_PRICE * orderCount);

    cartGameService.updateCartGame(TEST_GAME_ID, orderCount, TEST_USER_ID);

    orderService.registerOrder(TEST_USER);

    // when
    OrderGetDTO orderGetDTO = orderService.getOrder(TEST_USER_NAME, orderId);

    // then
    assertEquals(orderGetDTO.totalPrice(), totalPrice);
    assertEquals(orderGetDTO.userName(), TEST_USER_NAME);
    assertEquals(orderGetDTO.orderGameList().size(), 1);
  }

  @Test
  @DisplayName("주문 조회 -존재하지 않는 주문 Id 요청시 예외 발생")
  void getOrderByWrongId() {
    // given
    Long wrongOrderId = 136L;
    int orderCount = 2;
    int totalPrice = (TEST_GAME_PRICE * orderCount);

    cartGameService.updateCartGame(TEST_GAME_ID, orderCount, TEST_USER_ID);

    orderService.registerOrder(TEST_USER);

    // when,then
    Exception exception = assertThrows(OrderNotFoundException.class,
        () -> orderService.getOrder(TEST_USER_NAME, wrongOrderId));
    assertEquals("해당 주문을 찾을 수 없습니다.", exception.getMessage());

  }

  @Test
  @DisplayName("주문 조회 - 주문 전체 목록 조회")
  void getsOrder() {
    // given
    int orderCount = 5;
    int otherOrderCount = 8;
    int anotherOrderCount = 2;

    int firstTotalPrice =
        (TEST_GAME_PRICE * orderCount) + (ANOTRER_TEST_GAME_PRICE * otherOrderCount);
    int secondTotalPrice =
        (OTRER_TEST_GAME_PRICE * anotherOrderCount) + (ANOTRER_TEST_GAME_PRICE * otherOrderCount);


    cartGameService.updateCartGame(TEST_GAME_ID, orderCount, TEST_USER_ID);
    cartGameService.updateCartGame(ANOTRER_TEST_GAME_ID, otherOrderCount, TEST_USER_ID);

    orderService.registerOrder(TEST_USER); //첫번째 주문

    cartGameService.updateCartGame(OTRER_TEST_GAME_ID, anotherOrderCount, TEST_USER_ID);
    cartGameService.updateCartGame(ANOTRER_TEST_GAME_ID, otherOrderCount, TEST_USER_ID);

    orderService.registerOrder(TEST_USER); //두번째 주문

    // when
    List<OrderGetDTO> ordersGetDTOList = orderService.getsOrder(TEST_USER);

    // then
    assertEquals(ordersGetDTOList.size(),2);
    //ordersGetDTOList 생성 날짜 내림차순이라 인덱스 1번에 있는게 firstTotalPrice
    assertEquals(ordersGetDTOList.get(1).totalPrice(),firstTotalPrice);
    assertEquals(ordersGetDTOList.get(0).totalPrice(),secondTotalPrice);
  }

  @Test
  @DisplayName("주문 내역 삭제 - 주문Id로 주문 내역 삭제")
  void deleteOrder(){
    // given
    Long orderId = 1L;
    int orderCount = 2;

    cartGameService.updateCartGame(TEST_GAME_ID, orderCount, TEST_USER_ID);

    orderService.registerOrder(TEST_USER);

    // when
    orderService.deleteOrder(TEST_USER_ID,orderId);

    // then
    assertTrue(orderRepository.findById(orderId).isEmpty());
  }

  @Test
  @DisplayName("주문 내역 삭제 - 존재하지 않는 주문 Id 요청시 예외 발생")
  void deleteOrderByWrongId() {
    // given
    Long wrongOrderId = 136L;
    int orderCount = 2;

    cartGameService.updateCartGame(TEST_GAME_ID, orderCount, TEST_USER_ID);

    orderService.registerOrder(TEST_USER);

    // when,then
    Exception exception = assertThrows(OrderNotFoundException.class,
        () -> orderService.deleteOrder(TEST_USER_ID, wrongOrderId));
    assertEquals("해당 주문을 찾을 수 없습니다.", exception.getMessage());

  }

  @Test
  @DisplayName("주문 내역 삭제 - 요청한 주문 ID의 해당 주문에 있는 유저 정보랑 로그인중 유저랑 다르면 예외")
  void deleteOrderByAnotherUser(){
    // given
    Long orderId = 1L;
    int orderCount = 2;

    cartGameService.updateCartGame(TEST_GAME_ID, orderCount, TEST_USER_ID);

    orderService.registerOrder(TEST_USER);

    // when,then
    Exception exception = assertThrows(InconsistencyUserIdException.class,
        () -> orderService.deleteOrder(TEST_ANOTHER_USER_ID,orderId));
    assertEquals("유저 아이디가 일치하지 않습니다.", exception.getMessage());
  }

}
