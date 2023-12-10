package fourtalking.Nateam.order.service;

import fourtalking.Nateam.cartgame.entity.CartGame;
import fourtalking.Nateam.cartgame.service.CartGameService;
import fourtalking.Nateam.game.entity.Game;
import fourtalking.Nateam.game.service.GameService;
import fourtalking.Nateam.global.exception.order.OrderNotFoundException;
import fourtalking.Nateam.global.exception.review.InconsistencyUserIdException;
import fourtalking.Nateam.order.dto.OrderGameDTO;
import fourtalking.Nateam.order.dto.OrderGameGetAllDTO;
import fourtalking.Nateam.order.dto.OrderGetDTO;
import fourtalking.Nateam.order.dto.OrderRegisterDTO;
import fourtalking.Nateam.order.entity.Orders;
import fourtalking.Nateam.order.repository.OrderRepository;
import fourtalking.Nateam.orderGame.entity.OrderGame;
import fourtalking.Nateam.orderGame.service.OrderGameService;
import fourtalking.Nateam.user.entity.User;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final GameService gameService;
  private final CartGameService cartGameService;
  private final OrderGameService orderGameService;
  private final OrderRepository orderRepository;

  public Orders findById(Long orderId) {

    return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
  }

  // 주문하기 서비스
  @Transactional
  public OrderRegisterDTO registerOrder(User user) {

    //로그인중 유저 장바구니 정보 가져오기
    List<CartGame> cartGameList = cartGameService.findUserCart(user.getUserId());

    // 로그인중 유저 장바구니에 담긴 게임 총 가격 구하는 메소드
    int cartTotalPrice = getCartGamesTotalPrice(cartGameList);
    Orders orders = Orders.createOrders(cartTotalPrice, user.getUserId());
    orderRepository.save(orders);

    // 장바구니에 있는 목록들을 OrderGame에 save
    saveOrderGames(cartGameList, orders.getOrderId());

    // 장바구니에 있는 목록 정보로 OrderGameDTO로 변환
    List<OrderGameDTO> orderGameList = CartGameToOrderGameDTO(cartGameList);

    // 나중 리렉토링으로 카트게임 서비스에서 장바구니 삭제 메소드 추가
    cartGameService.allDeleteCart(user.getUserId());

    return OrderRegisterDTO.of(orders, user.getUserName(), orderGameList);

  }

  public int getCartGamesTotalPrice(List<CartGame> cartGameList) {
    int totalPrice = 0;
    for (CartGame cartGame : cartGameList) {
      Game game = gameService.findById(cartGame.getGameId());
      totalPrice += (game.getGamePrice() * cartGame.getOrderCount());
    }
    return totalPrice;
  }

  public void saveOrderGames(List<CartGame> cartGameList, Long orderId) {
    for (CartGame cartGame : cartGameList) {
      OrderGame orderGame = OrderGame.createOrderGame(cartGame.getOrderCount(),
          cartGame.getGameId(), orderId);

      orderGameService.saveOrderGame(orderGame);
    }
  }

  public List<OrderGameDTO> CartGameToOrderGameDTO(List<CartGame> cartGameList) {
    List<OrderGameDTO> orderGameList = new ArrayList<>();

    for (CartGame cartGame : cartGameList) {
      Game game = gameService.findById(cartGame.getGameId());
      orderGameList.add(OrderGameDTO.of(game, cartGame.getOrderCount()));
    }

    return orderGameList;
  }


  // 주문 조회 서비스
  public OrderGetDTO getOrder(String userName, Long orderId) {

    Orders orders = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);

    List<OrderGame> orderGameList = orderGameService.findAllByOrderId(orderId);

    List<OrderGameDTO> orderGameDTOList = new ArrayList<>();

    for (OrderGame orderGame : orderGameList) {
      Game game = gameService.findById(orderGame.getGameId());

      OrderGameDTO orderGameDTO = OrderGameDTO.of(game, orderGame.getOrderCount());
      orderGameDTOList.add(orderGameDTO);
    }

    return OrderGetDTO.of(orders, userName, orderGameDTOList);
  }

  // 주문 전체 목록 조회 서비스
  public List<OrderGetDTO> getsOrder(User user) {

    List<OrderGameGetAllDTO> orderGameGetAllDTOs =
        orderRepository.findAllOrderGetDTOOrderByCreatedTime(user.getUserId());

    List<OrderGetDTO> orderGetDTOList = new ArrayList<>();

    Long checkOrderId = 0L;
    for (OrderGameGetAllDTO OGGADTO : orderGameGetAllDTOs) {
      if (!(OGGADTO.orderId().equals(checkOrderId))) {
        checkOrderId = OGGADTO.orderId();

        orderGetDTOList.add(
            OrderGetDTO.of(OGGADTO.orderId(), OGGADTO.orderTotalPrice(), OGGADTO.userName(),
                OGGADTO.createdTime(), new ArrayList<>()));
      }

      addOrderGameDTO(orderGetDTOList, OGGADTO.gameId(), OGGADTO.gameName(), OGGADTO.gamePrice(),
          OGGADTO.orderCount());

    }
    return orderGetDTOList;
  }

  public List<OrderGetDTO> addOrderGameDTO(List<OrderGetDTO> orderGetDTOList, Long gameId,
      String gameName, int gamePrice, int orderCount) {

    int lastIndex = orderGetDTOList.size() - 1;

    orderGetDTOList.get(lastIndex).addOrderGameList(
        OrderGameDTO.of(gameId, gameName, gamePrice, orderCount));

    return orderGetDTOList;
  }

  @Transactional
  public void deleteOrder(Long userId, Long orderId) {

    Orders orders = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);

    if (!userId.equals(orders.getUserId())) {

      throw new InconsistencyUserIdException();
    }

    orderGameService.deleteAllByOrderId(orderId);
    orderRepository.deleteById(orderId);
  }


}