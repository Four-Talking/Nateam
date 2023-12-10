package fourtalking.Nateam.order.dto;

import fourtalking.Nateam.order.entity.Orders;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderGetDTO(Long orderId, int totalPrice, String userName, LocalDateTime createdDate,
                       List<OrderGameDTO> orderGameList) {

  public static OrderGetDTO of(Orders orders,String userName,List<OrderGameDTO> orderGameList){
    return OrderGetDTO.builder()
        .orderId(orders.getOrderId())
        .totalPrice(orders.getOrderTotalPrice())
        .userName(userName)
        .createdDate(orders.getCreatedTime())
        .orderGameList(orderGameList)
        .build();
  }

  public static OrderGetDTO of(Long orderId,int totalPrice,String userName,
                               LocalDateTime createdDate ,List<OrderGameDTO> orderGameList){
    return OrderGetDTO.builder()
        .orderId(orderId)
        .totalPrice(totalPrice)
        .userName(userName)
        .createdDate(createdDate)
        .orderGameList(orderGameList)
        .build();
  }

  public void addOrderGameList(OrderGameDTO orderGameDTO){

    this.orderGameList.add(orderGameDTO);

  }



}