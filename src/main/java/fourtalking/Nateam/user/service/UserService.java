package fourtalking.Nateam.user.service;

import fourtalking.Nateam.global.exception.user.ExistingUserException;
import fourtalking.Nateam.passwordhistory.entity.PasswordHistory;
import fourtalking.Nateam.passwordhistory.repository.PasswordHistoryRepository;
import fourtalking.Nateam.user.constant.UserRole;
import fourtalking.Nateam.user.dto.SignupDTO;
import fourtalking.Nateam.user.entity.User;
import fourtalking.Nateam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PasswordHistoryRepository passwordHistoryRepository;

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

    private void savePassword(User user) {

        PasswordHistory password = PasswordHistory.builder()
                .password(user.getPassword())
                .userId(user.getUserId())
                .build();
        passwordHistoryRepository.save(password);
    }
}
