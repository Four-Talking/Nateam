package fourtalking.Nateam.review.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record GetAllReviewDTO(
        String userName,
        Long reviewId,
        String reviewContent,
        int reviewRank,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
) {
}