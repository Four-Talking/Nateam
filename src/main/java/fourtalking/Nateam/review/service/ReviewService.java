package fourtalking.Nateam.review.service;

import fourtalking.Nateam.global.exception.review.InconsistencyUserIdException;
import fourtalking.Nateam.global.exception.review.ReviewNotFoundException;
import fourtalking.Nateam.review.dto.GetAllReviewDTO;
import fourtalking.Nateam.review.dto.GetReviewDTO;
import fourtalking.Nateam.review.dto.ReviewRegisterDTO;
import fourtalking.Nateam.review.dto.UpdateReviewDTO;
import fourtalking.Nateam.review.entity.Review;
import fourtalking.Nateam.review.repository.ReviewRepository;
import fourtalking.Nateam.user.entity.User;
import fourtalking.Nateam.user.service.UserService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final UserService userService;
    private final ReviewRepository reviewRepository;

    public Review findById(Long reviewId) {

        return reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
    }

    public ReviewRegisterDTO.Response registerReview(Long userId, Long gameId, ReviewRegisterDTO.Request reviewRequest) {

        Review review = reviewRepository.save(reviewRequest.toEntity(userId, gameId));
        String userName = getUserName(review);

        return ReviewRegisterDTO.Response.of(userName, review);
    }

    public GetReviewDTO getReview(Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        String userName = getUserName(review);

        return GetReviewDTO.of(userName, review);
    }

    public List<GetAllReviewDTO> getAllReview(Long gameId) {

        return reviewRepository.findAllReviewByGameId(gameId);
    }

    @Transactional
    public UpdateReviewDTO.Response updateReview(Long userId, Long reviewId,
            UpdateReviewDTO.Request request) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        String userName = getUserName(review);

        if(!userId.equals(review.getUserId())){
            throw new InconsistencyUserIdException();
        }

        review.modify(request);

        return UpdateReviewDTO.Response.of(userName, review, request);
    }

    @Transactional
    public void deleteReview(Long userId, Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);

        if(!userId.equals(review.getUserId())){
            throw new InconsistencyUserIdException();
        }

        reviewRepository.deleteById(reviewId);
    }

    private String getUserName(Review review){

        User user = userService.findById(review.getUserId());

        return user.getUserName();
    }
}







