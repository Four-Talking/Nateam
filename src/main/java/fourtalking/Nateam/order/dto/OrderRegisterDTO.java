package fourtalking.Nateam.order.dto;

import fourtalking.Nateam.order.entity.Orders;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderRegisterDTO(Long orderId, int totalPrice, String userName, LocalDateTime createdDate,
                           List<OrderGameDTO> orderGameList) {

  public static OrderRegisterDTO of(Orders orders, String userName, List<OrderGameDTO> orderGameList){

    return OrderRegisterDTO.builder()
        .orderId(orders.getOrderId())
        .totalPrice(orders.getOrderTotalPrice())
        .userName(userName)
        .createdDate(orders.getCreatedTime())
        .orderGameList(orderGameList)
        .build();
  }
}
