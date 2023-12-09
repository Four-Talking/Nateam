package fourtalking.Nateam.review.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import fourtalking.Nateam.review.dto.GetAllReviewDTO;
import fourtalking.Nateam.review.dto.GetReviewDTO;
import fourtalking.Nateam.review.dto.ReviewRegisterDTO;
import fourtalking.Nateam.review.dto.ReviewRegisterDTO.Request;
import fourtalking.Nateam.review.dto.ReviewRegisterDTO.Response;
import fourtalking.Nateam.review.repository.ReviewRepository;
import fourtalking.Nateam.test.CommonTest;
import fourtalking.Nateam.user.dto.SignupDTO;
import fourtalking.Nateam.user.entity.User;
import fourtalking.Nateam.user.service.UserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReviewServiceTest implements CommonTest {

    @Autowired
    UserService userService;
//    @Autowired
//    private final GameService gameService;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ReviewService reviewService;

    @BeforeEach
    public void setup() {
        SignupDTO signRequestDTO = new SignupDTO(TEST_USER_NAME,TEST_USER_PASSWORD);
        userService.signup(signRequestDTO);
    }

    @Test
    @DisplayName("리뷰 생성 테스트")
    void test1() {

        // given
        Long gameId = 1L;
        String reviewContent = "review";
        int reviewRank = 2;
        ReviewRegisterDTO.Request reviewRequest = new Request(reviewContent,reviewRank);

        // when
        Response response = reviewService.registerReview(TEST_USER_ID, gameId, reviewRequest);

        // then
        assertNotNull(response.userName());
        assertEquals(TEST_USER_NAME, response.userName());
        assertEquals(gameId, response.gameId());
        assertEquals(reviewContent, response.reviewContent());
        assertEquals(reviewRank, response.reviewRank());
    }

    @Test
    @DisplayName("리뷰 조회 테스트")
    void test2() {

        // given
        Long gameId = 1L;
        String reviewContent = "review";
        int reviewRank = 2;
        ReviewRegisterDTO.Request reviewRequest = new Request(reviewContent,reviewRank);
        Response response = reviewService.registerReview(TEST_USER_ID, gameId, reviewRequest);

        Long reviewId = response.reviewId();

        // when
        GetReviewDTO getReviewDTO = reviewService.getReview(reviewId);

        // then
        assertEquals(TEST_USER_NAME, getReviewDTO.userName());
        assertEquals(gameId, getReviewDTO.gameId());
        assertEquals(reviewContent, getReviewDTO.reviewContent());
        assertEquals(reviewRank, getReviewDTO.reviewRank());
        assertEquals(reviewId, getReviewDTO.reviewId());
    }

    @Test
    @DisplayName("리뷰 전체 조회 테스트")
    void test3() {

        // given
        Long gameId = 1L;
        String reviewContent = "review";
        int reviewRank = 2;
        ReviewRegisterDTO.Request reviewRequest = new Request(reviewContent,reviewRank);
        Response response = reviewService.registerReview(TEST_USER_ID, gameId, reviewRequest);

        String anotherReviewContent = "review2";
        int anotherReviewRank = 3;
        ReviewRegisterDTO.Request anotherReviewRequest = new Request(anotherReviewContent,anotherReviewRank);
        Response anotherResponse = reviewService.registerReview(TEST_USER_ID, gameId, anotherReviewRequest);

        Long reviewId = response.reviewId();
        Long anotherReviewId = anotherResponse.reviewId();

        // when
        List<GetAllReviewDTO> getAllReviewDTOList = reviewService.getAllReview(gameId);

        // then
        assertEquals(TEST_USER_NAME, getAllReviewDTOList.get(1).userName());
        assertEquals(TEST_USER_NAME, getAllReviewDTOList.get(0).userName());

        assertEquals(reviewId, getAllReviewDTOList.get(1).reviewId());
        assertEquals(anotherReviewId, getAllReviewDTOList.get(0).reviewId());

        assertEquals(reviewContent, getAllReviewDTOList.get(1).reviewContent());
        assertEquals(anotherReviewContent, getAllReviewDTOList.get(0).reviewContent());

        assertEquals(reviewRank, getAllReviewDTOList.get(1).reviewRank());
        assertEquals(anotherReviewRank, getAllReviewDTOList.get(0).reviewRank());
    }
}