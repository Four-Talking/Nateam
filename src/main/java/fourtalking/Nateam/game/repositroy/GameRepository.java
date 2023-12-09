package fourtalking.Nateam.game.repositroy;

import fourtalking.Nateam.game.dto.GameGetDTO;
import fourtalking.Nateam.game.entity.Game;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("select "
            + "new fourtalking.Nateam.game.dto.GameGetDTO("
            + "g.gameId, g.gameName, g.gameIntroduction, g.gamePrice, "
            + "coalesce(avg(r.reviewRank), 0) , u.userName, g.createdTime, g.lastModifiedTime) "
            + "from Game g "
            + "left outer join Review r on g.gameId = r.gameId "
            + "inner join User u on g.userId = u.userId "
            + "group by g.gameId "
            + "order by g.createdTime desc")
    List<GameGetDTO> findAllGameGetDTOOrderByCreatedTime();

    @Query("select coalesce(avg(r.reviewRank), 0) from Game g left outer join Review r on g.gameId = r.gameId group by g.gameId having g.gameId = :gameId")
    Double getGameReviewRank(@Param("gameId") Long gameId);

}
