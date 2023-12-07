package fourtalking.Nateam.review.service;

import fourtalking.Nateam.global.exception.review.ReviewNotFoundException;
import fourtalking.Nateam.review.dto.GetAllReviewDTO;
import fourtalking.Nateam.review.dto.GetReviewDTO;
import fourtalking.Nateam.review.dto.ReviewRegisterDTO;
import fourtalking.Nateam.review.dto.UpdateReviewDTO;
import fourtalking.Nateam.review.entity.Review;
import fourtalking.Nateam.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import java.util.List;
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

        Review review = reviewRepository.save(reviewRequest.toEntity(gameId));

        return ReviewRegisterDTO.Response.of(review);
    }

    public GetReviewDTO getReview(String userName, Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);

        return GetReviewDTO.of(userName, review);
    }

    public List<GetAllReviewDTO> getAllReview(Long gameId) {

        return reviewRepository.findAllReviewByGameId(gameId);
    }

    @Transactional
    public UpdateReviewDTO.Response updateReview(Long reviewId, UpdateReviewDTO.Request request) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        review.modify(request);

        return UpdateReviewDTO.Response.of(review, request);
    }

    @Transactional
    public void deleteReview(Long reviewId) {

        reviewRepository.deleteById(reviewId);
    }
}







