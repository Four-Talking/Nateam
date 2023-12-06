package fourtalking.Nateam.review.service;

import fourtalking.Nateam.review.dto.ReviewRegisterDTO;
import fourtalking.Nateam.review.entity.Review;
import fourtalking.Nateam.review.repository.ReviewRepository;
import fourtalking.Nateam.global.exception.review.ReviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
    }

    public ReviewRegisterDTO.Response registerReview(Long gameId, ReviewRegisterDTO.Request reviewRequest) {
        Review review =  reviewRepository.save(reviewRequest.toEntity(gameId));

        return ReviewRegisterDTO.Response.of(review);
    }
}






