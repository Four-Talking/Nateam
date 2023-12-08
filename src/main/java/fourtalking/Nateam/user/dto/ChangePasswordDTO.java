package fourtalking.Nateam.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ChangePasswordDTO(
        @NotBlank String existingPassword,
        @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*?_]{8,15}$") String newPassword) {

}
