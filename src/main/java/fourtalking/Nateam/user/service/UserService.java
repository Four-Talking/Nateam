package fourtalking.Nateam.user.service;

import fourtalking.Nateam.global.exception.user.ExistingUserException;
import fourtalking.Nateam.global.exception.user.UserNotFoundException;
import fourtalking.Nateam.global.exception.user.WrongPasswordException;
import fourtalking.Nateam.global.security.jwt.JwtUtil;
import fourtalking.Nateam.global.security.userdetails.UserDetailsImpl;
import fourtalking.Nateam.passwordhistory.entity.PasswordHistory;
import fourtalking.Nateam.passwordhistory.repository.PasswordHistoryRepository;
import fourtalking.Nateam.user.constant.UserRole;
import fourtalking.Nateam.user.dto.EditProfileDTO;
import fourtalking.Nateam.user.dto.EditProfileDTO.Response;
import fourtalking.Nateam.user.dto.LoginDTO;
import fourtalking.Nateam.user.dto.SignupDTO;
import fourtalking.Nateam.user.entity.User;
import fourtalking.Nateam.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PasswordHistoryRepository passwordHistoryRepository;
    private final JwtUtil jwtUtil;

    private static final String BASIC_PROFILE_NICKNAME = "nick";
    private static final String BASIC_PROFILE_INTRODUCTION = "Hello World!";

    public SignupDTO.Response signup(SignupDTO.Request signRequestDTO) {

        String userName = signRequestDTO.userName();
        String password = signRequestDTO.password();

        if (userRepository.findByUserName(userName).isPresent()) {
            throw new ExistingUserException();
        }

        User user = User.builder()
                .userName(userName)
                .password(passwordEncoder.encode(password))
                .nickname(BASIC_PROFILE_NICKNAME)
                .userIntroduce(BASIC_PROFILE_INTRODUCTION)
                .userRole(UserRole.USER)
                .build();

        userRepository.save(user);

        savePassword(user);

        return SignupDTO.Response.builder()
                .message("회원가입 성공")
                .build();
    }

    public LoginDTO.Response login(LoginDTO.Request loginRequestDTO, HttpServletResponse response) {

        String username = loginRequestDTO.userName();
        String password = loginRequestDTO.password();

        User user = userRepository.findByUserName(username).orElseThrow(UserNotFoundException::new);

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new WrongPasswordException();
        }

        response.setHeader("Authorization", jwtUtil.createToken(username, true));
        return LoginDTO.Response.builder()
                .message("로그인 성공")
                .build();
    }

    public void logout(HttpServletResponse response) {

        response.setHeader("Authorization", jwtUtil.createToken(null, false));
    }

    @Transactional
    public Response editProfile(EditProfileDTO.Request editProfileRequestDTO, UserDetailsImpl userDetails) {

        User user = userRepository.findById(userDetails.getUser().getUserId())
                .orElseThrow(UserNotFoundException::new);

        if(!passwordEncoder.matches(editProfileRequestDTO.password(), user.getPassword())) {
            throw new WrongPasswordException();
        }

        String nickname = editProfileRequestDTO.nickname();
        String introduction = editProfileRequestDTO.introduction();

        user.editProfile(nickname, introduction);

        return EditProfileDTO.Response.of(user);
    }

    private void savePassword(User user) {

        PasswordHistory password = PasswordHistory.builder()
                .password(user.getPassword())
                .userId(user.getUserId())
                .build();
        passwordHistoryRepository.save(password);
    }
}
