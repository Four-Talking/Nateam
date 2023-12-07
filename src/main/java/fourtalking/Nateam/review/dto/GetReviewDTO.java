package fourtalking.Nateam.review.dto;

import fourtalking.Nateam.review.entity.Review;
import fourtalking.Nateam.user.entity.User;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record GetReviewDTO(
        String userName,
        Long gameId,
        Long reviewId,
        String reviewContent,
        int reviewRank,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
) {

    public static GetReviewDTO of(User user, Review review){

        return GetReviewDTO.builder()
                .gameId(review.getGameId())
                .userName(user.getUserName())
                .reviewId(review.getReviewId())
                .reviewContent(review.getReviewContent())
                .reviewRank(review.getReviewRank())
                .createdDate(review.getCreatedTime())
                .lastModifiedDate(review.getLastModifiedTime())
                .build();
    }
}