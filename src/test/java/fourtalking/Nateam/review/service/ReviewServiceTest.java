package fourtalking.Nateam.review.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import fourtalking.Nateam.review.dto.ReviewRegisterDTO;
import fourtalking.Nateam.review.dto.ReviewRegisterDTO.Request;
import fourtalking.Nateam.review.dto.ReviewRegisterDTO.Response;
import fourtalking.Nateam.review.repository.ReviewRepository;
import fourtalking.Nateam.test.CommonTest;
import fourtalking.Nateam.user.dto.SignupDTO;
import fourtalking.Nateam.user.entity.User;
import fourtalking.Nateam.user.service.UserService;
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
}