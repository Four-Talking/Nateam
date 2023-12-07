package fourtalking.Nateam.global.security.userdetails;

import fourtalking.Nateam.global.exception.user.UserNotFoundException;
import fourtalking.Nateam.user.entity.User;
import fourtalking.Nateam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsImpl getUserDetails(String username) {

        User user = userRepository.findByUserName(username)
                .orElseThrow(UserNotFoundException::new);

        return new UserDetailsImpl(user);
    }
}
