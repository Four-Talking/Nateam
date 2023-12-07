package fourtalking.Nateam.passwordhistory.entity;

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
public class PasswordHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long passwordHistoryId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Long userId;

    public static PasswordHistory createPasswordHistory(String password, Long userId) {
        return PasswordHistory.builder()
                .password(password)
                .userId(userId)
                .build();
    }
}
