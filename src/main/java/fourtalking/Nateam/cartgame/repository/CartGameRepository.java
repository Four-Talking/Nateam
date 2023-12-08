package fourtalking.Nateam.cartgame.repository;

import fourtalking.Nateam.cartgame.entity.CartGame;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartGameRepository extends JpaRepository<CartGame,Long> {

  List<CartGame> findCartsByUserId(Long userId);

  void deleteAllByUserId(Long userId);
}
