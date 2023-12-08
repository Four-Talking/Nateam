package fourtalking.Nateam.user.service;

import fourtalking.Nateam.global.exception.user.DuplicatePasswordException;
import fourtalking.Nateam.global.exception.user.ExistingUserException;
import fourtalking.Nateam.global.exception.user.InvalidUserIdAndPasswordException;
import fourtalking.Nateam.global.exception.user.UserNotFoundException;
import fourtalking.Nateam.global.exception.user.WrongPasswordException;
import fourtalking.Nateam.global.security.jwt.JwtUtil;
import fourtalking.Nateam.global.security.userdetails.UserDetailsImpl;
import fourtalking.Nateam.passwordhistory.entity.PasswordHistory;
import fourtalking.Nateam.passwordhistory.service.PasswordHistoryService;
import fourtalking.Nateam.user.dto.ChangePasswordDTO;
import fourtalking.Nateam.user.dto.EditProfileDTO;
import fourtalking.Nateam.user.dto.EditProfileDTO.Response;
import fourtalking.Nateam.user.dto.LoginDTO;
import fourtalking.Nateam.user.dto.SignupDTO;
import fourtalking.Nateam.user.entity.User;
import fourtalking.Nateam.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final PasswordHistoryService passwordHistoryService;

    private final JwtUtil jwtUtil;


    public void signup(SignupDTO signRequestDTO) {

        validateExistingUser(signRequestDTO.userName());

        User user = signRequestDTO.toEntity(passwordEncoder);

        userRepository.save(user);
        savePasswordHistory(user.getUserId(), user.getPassword());
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

    @Transactional
    public void changePassword(ChangePasswordDTO changePasswordDTO, Long userId) {

        String existingPassword = changePasswordDTO.existingPassword();
        String newPassword = changePasswordDTO.newPassword();

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        validateChangingPassword(user, existingPassword, newPassword);

        user.changePassword(passwordEncoder.encode(newPassword));
        savePasswordHistory(userId, newPassword);
    }

    public User findById(Long userId) {

        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    private void savePasswordHistory(Long userId, String password) {

        PasswordHistory passwordHistory =
                PasswordHistory.createPasswordHistory(password, userId);

        passwordHistoryService.savePasswordHistory(passwordHistory);
    }

    private boolean isPasswordWithinLast3Times(Long userId, String newPassword) {

        List<PasswordHistory> passwordHistoryList =
                passwordHistoryService.findTop3PasswordHistory(userId);

        for (PasswordHistory history : passwordHistoryList) {
            if (passwordEncoder.matches(newPassword, history.getPassword())) {
                return false;
            }
        }
        return true;
    }

    private void validateChangingPassword(User user, String existingPassword, String newPassword) {

        if (!passwordEncoder.matches(existingPassword, user.getPassword())) {
            throw new WrongPasswordException();
        }

        if (!isPasswordWithinLast3Times(user.getUserId(), newPassword)) {
            throw new DuplicatePasswordException();
        }
    }
}
