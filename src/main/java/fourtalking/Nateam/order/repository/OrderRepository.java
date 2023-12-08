package fourtalking.Nateam.order.repository;

import fourtalking.Nateam.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,Long> {

}
