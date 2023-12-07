package fourtalking.Nateam.user.dto;

import lombok.Builder;

public class LoginDTO {

    public record Request(String userName, String password) {

    }

    @Builder
    public record Response(String message) {

    }
}