package fourtalking.Nateam.passwordhistory.service;

import fourtalking.Nateam.passwordhistory.entity.PasswordHistory;
import fourtalking.Nateam.passwordhistory.repository.PasswordHistoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordHistoryService {

    private final PasswordHistoryRepository passwordHistoryRepository;

    public void savePasswordHistory(PasswordHistory passwordHistory) {
        passwordHistoryRepository.save(passwordHistory);
    }

    public List<PasswordHistory> findTop3PasswordHistory(Long userId) {

        return passwordHistoryRepository.findTop3ByUserIdOrderByCreatedTimeDesc(userId);
    }
}