package fourtalking.Nateam.review.dto;

import fourtalking.Nateam.review.entity.Review;
import fourtalking.Nateam.user.entity.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public class UpdateReviewDTO {

    public record Request(
            @NotBlank @Size(max = 255) String reviewContent,
            @Min(1) @Max(5) int reviewRank) {
    }

    @Builder
    public record Response(Long gameId, String userName, Long reviewId, String reviewContent,
                           int reviewRank, LocalDateTime createdDate,
                           LocalDateTime lastModifiedDate) {

        public static UpdateReviewDTO.Response of(User user, Review review, UpdateReviewDTO.Request request) {
            return Response.builder()
                    .gameId(review.getGameId())
                    .userName(user.getUserName())
                    .reviewId(review.getReviewId())
                    .reviewContent(request.reviewContent)
                    .reviewRank(request.reviewRank)
                    .createdDate(review.getCreatedTime())
                    .lastModifiedDate(review.getLastModifiedTime())
                    .build();
        }
    }
}