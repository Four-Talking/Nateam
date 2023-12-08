package fourtalking.Nateam.user.dto;

import fourtalking.Nateam.user.constant.UserRole;
import fourtalking.Nateam.user.entity.User;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.crypto.password.PasswordEncoder;

public record SignupDTO(
        @Pattern(regexp = "^[a-z0-9]{4,10}$") String userName,
        @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*?_]{8,15}$") String password) {

    private static final String BASIC_PROFILE_NICKNAME = "nick";
    private static final String BASIC_PROFILE_INTRODUCTION = "Hello World!";

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .userName(userName)
                .password(passwordEncoder.encode(password))
                .nickname(BASIC_PROFILE_NICKNAME)
                .userIntroduce(BASIC_PROFILE_INTRODUCTION)
                .userRole(UserRole.USER)
                .build();
    }
}
