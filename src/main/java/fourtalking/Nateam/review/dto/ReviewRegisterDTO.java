package fourtalking.Nateam.review.dto;

import fourtalking.Nateam.review.entity.Review;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Builder;

public class ReviewRegisterDTO{
    public record Request(
            @NotBlank @Size String reviewContent,
            @NotBlank @Max(5) int reviewRank) {
        public Review toEntity(Long gameId) {
            return Review.builder()
                    .userId(1L)
                    .gameId(gameId)
                    .reviewContent(reviewContent)
                    .reviewRank(reviewRank)
                    .build();
        }

    }
    @Builder
    public record Response(Long userId, Long gameId, String reviewContent, int reviewRank, LocalDateTime createdDate) {

        public static Response of(Review review) {
            return Response.builder()
                    .userId(review.getUserId())
                    .gameId(review.getGameId())
                    .reviewContent(review.getReviewContent())
                    .reviewRank(review.getReviewRank())
                    .createdDate(review.getCreatedTime())
                    .build();
        }
    }
}

