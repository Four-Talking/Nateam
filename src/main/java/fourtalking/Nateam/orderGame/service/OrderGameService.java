package fourtalking.Nateam.orderGame.service;

import fourtalking.Nateam.orderGame.entity.OrderGame;
import fourtalking.Nateam.orderGame.orderGameRepository.OrderGameRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderGameService {

  private final OrderGameRepository orderGameRepository;

  public void saveOrderGame(OrderGame orderGame){

    orderGameRepository.save(orderGame);
  }

  public List<OrderGame> findAllByOrderId(Long orderId){

    return orderGameRepository.findAllByOrderId(orderId);

  }

  public void deleteAllByOrderId(Long orderId) {

    orderGameRepository.deleteAllByOrderId(orderId);
  }
}
