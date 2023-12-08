package fourtalking.Nateam.user.entity;

import fourtalking.Nateam.user.constant.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false, length = 20)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 10)
    private String nickname;

    @Column(nullable = false)
    private String userIntroduce;

    @Column(nullable = false)
    private UserRole userRole;

    public void editProfile(String nickname, String userIntroduce) {
        this.nickname = nickname;
        this.userIntroduce = userIntroduce;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

}
