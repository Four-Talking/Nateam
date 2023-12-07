package fourtalking.Nateam.review.repository;

import fourtalking.Nateam.review.dto.GetAllReviewDTO;
import fourtalking.Nateam.review.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select "
            + "new fourtalking.Nateam.review.dto.GetAllReviewDTO("
            + "u.userName, r.reviewId, r.reviewContent, r.reviewRank, r.createdTime, "
            + "r.lastModifiedTime) "
            + "from Review r "
            + "inner join User u on r.userId = u.userId "
            + "where r.gameId = ?1 "
            + "order by r.createdTime desc")
    List<GetAllReviewDTO> findAllReviewByGameId(Long gameId);
}


