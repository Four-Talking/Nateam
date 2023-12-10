package fourtalking.Nateam.order.repository;

import fourtalking.Nateam.order.dto.OrderGameGetAllDTO;
import fourtalking.Nateam.order.entity.Orders;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Orders,Long> {

  @Query("SELECT " +
      "new fourtalking.Nateam.order.dto.OrderGameGetAllDTO(" +
      "o.orderId, o.orderTotalPrice, u.userName, o.createdTime, "
      + "g.gameId, g.gameName, g.gamePrice, og.orderCount" +
      ") " +
      "FROM Orders o " +
      "INNER JOIN User u ON u.userId = o.userId " +
      "INNER JOIN OrderGame og ON og.orderId = o.orderId " +
      "INNER JOIN Game g on og.gameId = g.gameId " +
      "where o.userId = :userId "
      + "order by o.createdTime desc"
  )
  List<OrderGameGetAllDTO> findAllOrderGetDTOOrderByCreatedTime(@Param("userId") Long userId);




}
