package fourtalking.Nateam.game.repositroy;

import fourtalking.Nateam.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

}
