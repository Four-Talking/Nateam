package fourtalking.Nateam.review.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import fourtalking.Nateam.review.dto.ReviewRegisterDTO;
import fourtalking.Nateam.review.dto.ReviewRegisterDTO.Request;
import fourtalking.Nateam.review.entity.Review;
import fourtalking.Nateam.review.repository.ReviewRepository;
import fourtalking.Nateam.user.entity.User;
import fourtalking.Nateam.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 서버의 PORT 를 랜덤으로 설정합니다.
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스의 생성 단위를 클래스로 변경합니다.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReviewServiceTest {

    @Autowired
    UserService userService;
//    @Autowired
//    GameService gameService;
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    @Order(1)
    @DisplayName("리뷰 등록")
    void test1() {

        // given
        Long userId = 1L;
        Long gameId = 1L;
        String reviewContent = "hi";
        int reviewRank = 2;
        ReviewRegisterDTO.Request reviewRequest = new Request(reviewContent,reviewRank);

        // when
        Review review = reviewRepository.save(reviewRequest.toEntity(userId,gameId));

        //String userName = getUserName(review);

        //ReviewRegisterDTO.Response.of(userName, review);

        // then
        assertNotNull(review.getReviewId());
        assertEquals(userId, review.getUserId());
        assertEquals(gameId, review.getGameId());
        assertEquals(reviewContent, review.getReviewContent());
        assertEquals(reviewRank, review.getReviewRank());
    }

    private String getUserName(Review review){

        User user = userService.findById(review.getUserId());

        return user.getUserName();
    }
}