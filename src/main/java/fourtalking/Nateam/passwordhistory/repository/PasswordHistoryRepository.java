package fourtalking.Nateam.passwordhistory.repository;

import fourtalking.Nateam.passwordhistory.entity.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {

}
