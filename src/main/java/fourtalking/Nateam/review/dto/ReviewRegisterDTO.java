package fourtalking.Nateam.review.dto;

import fourtalking.Nateam.review.entity.Review;
import fourtalking.Nateam.user.entity.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Builder;

public class ReviewRegisterDTO{

    public record Request(
            @NotBlank @Size(max = 255) String reviewContent,
            @Min(1) @Max(5) int reviewRank) {
        public Review toEntity(Long userId, Long gameId) {
            return Review.builder()
                    .userId(userId)
                    .gameId(gameId)
                    .reviewContent(reviewContent)
                    .reviewRank(reviewRank)
                    .build();
        }
    }

    @Builder
    public record Response(Long gameId, String userName, Long reviewId, String reviewContent, int reviewRank, LocalDateTime createdDate) {

        public static Response of(User user, Review review) {
            return Response.builder()
                    .gameId(review.getGameId())
                    .userName(user.getUserName())
                    .reviewId(review.getReviewId())
                    .reviewContent(review.getReviewContent())
                    .reviewRank(review.getReviewRank())
                    .createdDate(review.getCreatedTime())
                    .build();
        }
    }
}

