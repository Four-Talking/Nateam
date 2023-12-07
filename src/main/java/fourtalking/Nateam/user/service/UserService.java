package fourtalking.Nateam.user.service;

import fourtalking.Nateam.global.exception.user.ExistingUserException;
import fourtalking.Nateam.global.exception.user.InvalidUserIdAndPasswordException;
import fourtalking.Nateam.global.exception.user.UserNotFoundException;
import fourtalking.Nateam.global.exception.user.WrongPasswordException;
import fourtalking.Nateam.global.security.jwt.JwtUtil;
import fourtalking.Nateam.global.security.userdetails.UserDetailsImpl;
import fourtalking.Nateam.passwordhistory.entity.PasswordHistory;
import fourtalking.Nateam.passwordhistory.repository.PasswordHistoryRepository;
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


    public void signup(SignupDTO signRequestDTO) {

        validateExistingUser(signRequestDTO.userName());

        User user = signRequestDTO.toEntity(passwordEncoder);

        userRepository.save(user);
        savePasswordHistory(user);
    }

    private void validateExistingUser(String userName) {
        userRepository.findByUserName(userName).ifPresent(user -> {
                    throw new ExistingUserException();
                });
    }

    public void login(LoginDTO.Request loginRequestDTO, HttpServletResponse response) {

        String username = loginRequestDTO.userName();
        String password = loginRequestDTO.password();

        User user = userRepository.findByUserName(username)
                .orElseThrow(InvalidUserIdAndPasswordException::new);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidUserIdAndPasswordException();
        }

        response.setHeader("Authorization", jwtUtil.createToken(username, true));
    }

    public void logout(HttpServletResponse response) {

        response.setHeader("Authorization", jwtUtil.createToken(null, false));
    }

    @Transactional
    public Response editProfile(EditProfileDTO.Request editProfileRequestDTO,
            UserDetailsImpl userDetails) {

        User user = userRepository.findById(userDetails.getUser().getUserId())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(editProfileRequestDTO.password(), user.getPassword())) {
            throw new WrongPasswordException();
        }

        String nickname = editProfileRequestDTO.nickname();
        String introduction = editProfileRequestDTO.introduction();

        user.editProfile(nickname, introduction);

        return EditProfileDTO.Response.of(user);
    }

    public User findById(Long userId) {

        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    private void savePasswordHistory(User user) {

        PasswordHistory passwordHistory =
                PasswordHistory.createPasswordHistory(user.getPassword(), user.getUserId());

        passwordHistoryRepository.save(passwordHistory);
    }
}
