package fourtalking.Nateam.game.entity;

import fourtalking.Nateam.common.BaseLastModifiedTimeEntity;
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
public class Game extends BaseLastModifiedTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gameId;

    @Column(nullable = false, length = 20)
    private String gameName;

    @Column(nullable = false)
    private String gameIntroduction;

    @Column(nullable = false)
    private int gamePrice;

    // JPA의 연관관계를 이용하지 않는 코드, 추후에 연관관계를 위한 리팩토링 가능성 있음
    @Column(nullable = false)
    private Long userId;

    public void modifyGame(String gameName, String gameIntroduction, int gamePrice) {
        this.gameName = gameName;
        this.gameIntroduction = gameIntroduction;
        this.gamePrice = gamePrice;
    }
}
