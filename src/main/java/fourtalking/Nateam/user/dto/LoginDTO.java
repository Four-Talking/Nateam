package fourtalking.Nateam.user.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {

    public record Request(
            @NotBlank String userName,
            @NotBlank String password) {

    }
}