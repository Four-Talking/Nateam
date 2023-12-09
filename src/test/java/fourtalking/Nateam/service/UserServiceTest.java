package fourtalking.Nateam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fourtalking.Nateam.global.exception.user.ExistingUserException;
import fourtalking.Nateam.global.security.jwt.JwtUtil;
import fourtalking.Nateam.passwordhistory.service.PasswordHistoryService;
import fourtalking.Nateam.test.CommonTest;
import fourtalking.Nateam.user.dto.LoginDTO;
import fourtalking.Nateam.user.dto.SignupDTO;
import fourtalking.Nateam.user.entity.User;
import fourtalking.Nateam.user.repository.UserRepository;
import fourtalking.Nateam.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@Transactional
public class UserServiceTest implements CommonTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordHistoryService passwordHistoryService;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    //PasswordHistory assert 꼭하기

    @BeforeEach
    void setup() {
    }

    @Test
    @Order(1)
    @DisplayName("신규 회원가입")
    void test1() {

        //given
        SignupDTO signupDTO = new SignupDTO(TEST_USER_NAME, TEST_USER_PASSWORD);

        //when
        userService.signup(signupDTO);
        User user = userRepository.findByUserName(TEST_USER_NAME).orElseThrow();

        //then
        assertEquals(TEST_USER_NAME, user.getUserName());
    }

    @Test
    @Order(2)
    @DisplayName("회원가입 실패 - 이미 존재하는 userName")
    void test2() {

        //given
        userRepository.saveAndFlush(TEST_USER);

        SignupDTO signupDTO = new SignupDTO(TEST_USER_NAME, TEST_USER_PASSWORD);

        //when-then
        assertThrows(ExistingUserException.class, () -> userService.signup(signupDTO));
    }

    @Test
    @Order(3)
    @DisplayName("로그인")
    void test3() {

        //given
        SignupDTO signupDTO = new SignupDTO(TEST_USER_NAME, TEST_USER_PASSWORD);
        userService.signup(signupDTO);

        LoginDTO.Request loginRequestDto = new LoginDTO.Request(TEST_USER_NAME, TEST_USER_PASSWORD);
        ServletWebRequest servletContainer = (ServletWebRequest) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletContainer.getResponse();

        //when
        userService.login(loginRequestDto, response);

        //then
        System.out.println(response.getHeader("Authorization"));
        assertNotNull(response.getHeader("Authorization"));
    }


    //    @Test
//    @Order(4)
//    @DisplayName("로그인 실패 - id 혹은 password 오류")

    //    @Test
//    @Order(4)
//    @DisplayName("프로필 수정")

    //    @Test
//    @Order(5)
//    @DisplayName("프로필 수정 실패 - userRepository에 없는 user")

    //    @Test
//    @Order(6)
//    @DisplayName("프로필 수정 실패 - 틀린 비밀번호 입력")

    //    @Test
//    @Order(7)
//    @DisplayName("비밀번호 변경")

    //    @Test
//    @Order(8)
//    @DisplayName("비밀번호 변경 실패 - 틀린 비밀번호 입력")

    //    @Test
//    @Order(9)
//    @DisplayName("비밀번호 변경 실패 - 신규 비밀번호가 최근 3번 이내의 비밀번호")

}
