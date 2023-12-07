package fourtalking.Nateam.user.dto;

import fourtalking.Nateam.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public class EditProfileDTO {

    public record Request(
            @Size String nickname,
            String introduction,
            @NotBlank String password) {

    }

    @Builder
    public record Response(String userName, String nickname, String userIntroduce) {

        public static Response of(User user) {
            return Response.builder()
                    .userName(user.getUserName())
                    .nickname(user.getNickname())
                    .userIntroduce(user.getUserIntroduce())
                    .build();
        }
    }
}
