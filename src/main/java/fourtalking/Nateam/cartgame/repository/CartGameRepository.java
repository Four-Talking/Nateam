package fourtalking.Nateam.cartgame.repository;

import fourtalking.Nateam.cartgame.entity.CartGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartGameRepository extends JpaRepository<CartGame,Long> {

}
