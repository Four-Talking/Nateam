package fourtalking.Nateam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fourtalking.Nateam.global.exception.user.DuplicatePasswordException;
import fourtalking.Nateam.global.exception.user.ExistingUserException;
import fourtalking.Nateam.global.exception.user.InvalidUserIdAndPasswordException;
import fourtalking.Nateam.global.exception.user.UserNotFoundException;
import fourtalking.Nateam.global.exception.user.WrongPasswordException;
import fourtalking.Nateam.global.security.jwt.JwtUtil;
import fourtalking.Nateam.passwordhistory.entity.PasswordHistory;
import fourtalking.Nateam.passwordhistory.service.PasswordHistoryService;
import fourtalking.Nateam.test.CommonTest;
import fourtalking.Nateam.user.dto.ChangePasswordDTO;
import fourtalking.Nateam.user.dto.EditProfileDTO;
import fourtalking.Nateam.user.dto.LoginDTO;
import fourtalking.Nateam.user.dto.SignupDTO;
import fourtalking.Nateam.user.entity.User;
import fourtalking.Nateam.user.repository.UserRepository;
import fourtalking.Nateam.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
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

    @Test
    @DisplayName("신규 회원가입")
    void test1() {

        //given
        SignupDTO signupDTO = new SignupDTO(TEST_USER_NAME, TEST_USER_PASSWORD);

        //when
        userService.signup(signupDTO);
        User user = userRepository.findByUserName(TEST_USER_NAME).orElseThrow();

        //then
        assertEquals(TEST_USER_NAME, user.getUserName());

        List<PasswordHistory> historyList = passwordHistoryService.findTop3PasswordHistory(user.getUserId());
        System.out.println(user.getUserId());
        assertEquals(1, historyList.size());
    }

    @Test
    @DisplayName("회원가입 실패 - 이미 존재하는 userName")
    void test2() {

        //given
        userRepository.saveAndFlush(TEST_USER);

        SignupDTO signupDTO = new SignupDTO(TEST_USER_NAME, TEST_USER_PASSWORD);

        //when-then
        assertThrows(ExistingUserException.class, () -> userService.signup(signupDTO));
    }

    @Test
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
        assertNotNull(response.getHeader("Authorization"));
    }

    @Test
    @DisplayName("로그인 실패 - 일치하지 않는 userName-password 오류")
    void test4() {

        //given
        SignupDTO signupDTO = new SignupDTO(TEST_USER_NAME, TEST_USER_PASSWORD);
        userService.signup(signupDTO);

        LoginDTO.Request loginRequestDto1 = new LoginDTO.Request(TEST_USER_NAME + "3", TEST_USER_PASSWORD);
        LoginDTO.Request loginRequestDto2 = new LoginDTO.Request(TEST_USER_NAME, TEST_USER_PASSWORD + "3");

        ServletWebRequest servletContainer = (ServletWebRequest) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletContainer.getResponse();

        //when - then
        assertThrows(InvalidUserIdAndPasswordException.class,
                () -> userService.login(loginRequestDto1, response));
        assertThrows(InvalidUserIdAndPasswordException.class,
                () -> userService.login(loginRequestDto2, response));

    }

    @Test
    @DisplayName("프로필 수정")
    void test5() {

        //given
        SignupDTO signupDTO = new SignupDTO(TEST_USER_NAME, TEST_USER_PASSWORD);
        userService.signup(signupDTO);

        String nickname = "나는야동하";
        String introduction = "나는 멋지고 효자야";
        EditProfileDTO.Request request = new EditProfileDTO.Request(
                nickname, introduction, TEST_USER_PASSWORD);

        User user = userRepository.findByUserName(TEST_USER_NAME).orElseThrow(UserNotFoundException::new);
        Long userId = user.getUserId();

        //when
        EditProfileDTO.Response response = userService.editProfile(request, userId);

        //then
        assertEquals(TEST_USER_NAME, response.userName());
        assertEquals(nickname, response.nickname());
        assertEquals(introduction, response.userIntroduce());
    }

    @Test
    @DisplayName("프로필 수정 실패 - userRepository에 없는 user")
    void test6() {

        //given
        SignupDTO signupDTO = new SignupDTO(TEST_USER_NAME, TEST_USER_PASSWORD);
        userService.signup(signupDTO);

        String nickname = "나는야동하";
        String introduction = "나는 멋지고 효자야";
        EditProfileDTO.Request request = new EditProfileDTO.Request(
                nickname, introduction, TEST_USER_PASSWORD);

        User user = userRepository.findByUserName(TEST_USER_NAME).orElseThrow(UserNotFoundException::new);
        Long userId = user.getUserId() + 1;

        //when-then
        assertThrows(UserNotFoundException.class, () -> userService.editProfile(request, userId));
    }

    @Test
    @DisplayName("프로필 수정 실패 - 틀린 비밀번호 입력")
    void test7() {
        //given
        SignupDTO signupDTO = new SignupDTO(TEST_USER_NAME, TEST_USER_PASSWORD);
        userService.signup(signupDTO);

        String nickname = "나는야동하";
        String introduction = "나는 멋지고 효자야";
        EditProfileDTO.Request request = new EditProfileDTO.Request(
                nickname, introduction, TEST_USER_PASSWORD + "3");

        User user = userRepository.findByUserName(TEST_USER_NAME).orElseThrow(UserNotFoundException::new);
        Long userId = user.getUserId();

        //when-then
        assertThrows(WrongPasswordException.class, () -> userService.editProfile(request, userId));
    }

    @Test
    @DisplayName("비밀번호 변경")
    void test8() {

        //given
        SignupDTO signupDTO = new SignupDTO(TEST_USER_NAME, TEST_USER_PASSWORD);
        userService.signup(signupDTO);

        String existingPassword = TEST_USER_PASSWORD;
        String newPassword = TEST_USER_PASSWORD + "!";

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO(existingPassword, newPassword);

        User user = userRepository.findByUserName(TEST_USER_NAME).orElseThrow(UserNotFoundException::new);
        Long userId = user.getUserId();

        //when
        userService.changePassword(changePasswordDTO, userId);

        //then
        User user1 = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        assertTrue(passwordEncoder.matches(newPassword, user1.getPassword()));

        List<PasswordHistory> historyList = passwordHistoryService.findTop3PasswordHistory(userId);
        assertEquals(2, historyList.size());
    }

    @Test
    @DisplayName("비밀번호 변경 실패 - 틀린 비밀번호 입력")
    void test9() {

        //given
        SignupDTO signupDTO = new SignupDTO(TEST_USER_NAME, TEST_USER_PASSWORD);
        userService.signup(signupDTO);

        String existingPassword = TEST_USER_PASSWORD + "!";
        String newPassword = TEST_USER_PASSWORD + "?";

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO(existingPassword, newPassword);

        User user = userRepository.findByUserName(TEST_USER_NAME).orElseThrow(UserNotFoundException::new);
        Long userId = user.getUserId();

        //when-then
        assertThrows(WrongPasswordException.class, () ->
                userService.changePassword(changePasswordDTO, userId));
    }

    @Test
    @DisplayName("비밀번호 변경 실패 - 신규 비밀번호가 최근 3번 이내의 비밀번호")
    void test10() {

        //given
        SignupDTO signupDTO = new SignupDTO(TEST_USER_NAME, TEST_USER_PASSWORD);
        userService.signup(signupDTO);

        ChangePasswordDTO changePasswordDTO1 = new ChangePasswordDTO(TEST_USER_PASSWORD, "12345679");
        ChangePasswordDTO changePasswordDTO2 = new ChangePasswordDTO("12345679", "12345677");
        ChangePasswordDTO changePasswordDTO3 = new ChangePasswordDTO("12345677", TEST_USER_PASSWORD);

        User user = userRepository.findByUserName(TEST_USER_NAME).orElseThrow(UserNotFoundException::new);
        Long userId = user.getUserId();

        //when-then
        userService.changePassword(changePasswordDTO1, userId);
        userService.changePassword(changePasswordDTO2, userId);

        assertThrows(DuplicatePasswordException.class,
                () -> userService.changePassword(changePasswordDTO3, userId));
    }

}
