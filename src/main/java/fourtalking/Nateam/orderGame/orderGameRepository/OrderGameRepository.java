package fourtalking.Nateam.orderGame.orderGameRepository;

import fourtalking.Nateam.orderGame.entity.OrderGame;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderGameRepository extends JpaRepository<OrderGame,Long> {

  List<OrderGame> findAllByOrderId(Long orderId);

  void deleteAllByOrderId(Long orderId);
}
