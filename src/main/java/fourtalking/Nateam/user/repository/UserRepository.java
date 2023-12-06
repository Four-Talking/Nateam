package fourtalking.Nateam.user.repository;

import fourtalking.Nateam.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username);

}
