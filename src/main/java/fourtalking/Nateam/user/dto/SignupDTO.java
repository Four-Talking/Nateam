package fourtalking.Nateam.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;

public class SignupDTO {

    public record Request(
            @Pattern(regexp = "^[a-z0-9]{4,10}$") String userName,
            @Pattern(regexp = "^[A-Za-z0-9]{8,15}$") String password) {

    }

    @Builder
    public record Response(String message) {

    }
}
