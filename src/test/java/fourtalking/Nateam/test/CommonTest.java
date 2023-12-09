package fourtalking.Nateam.test;

import fourtalking.Nateam.user.constant.UserRole;
import fourtalking.Nateam.user.entity.User;

public interface CommonTest {

    String ANOTHER_PREFIX = "another-";
    Long TEST_USER_ID = 1L;
    Long TEST_ANOTHER_USER_ID = 2L;
    String TEST_USER_NAME = "nateam";
    String TEST_USER_PASSWORD = "12345678";
    User TEST_USER = User.builder()
            .userName(TEST_USER_NAME)
            .password(TEST_USER_PASSWORD)
            .userRole(UserRole.USER)
            .nickname("nickname")
            .userIntroduce("introduce")
            .build();
    User TEST_ANOTHER_USER = User.builder()
            .userName(ANOTHER_PREFIX + TEST_USER_NAME)
            .password(ANOTHER_PREFIX + TEST_USER_PASSWORD)
            .userRole(UserRole.USER)
            .nickname("nickname")
            .userIntroduce("introduce")
            .build();
}
