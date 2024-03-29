package fourtalking.Nateam.game.service;

import fourtalking.Nateam.game.dto.GameGetDTO;
import fourtalking.Nateam.game.dto.GameModifyDTO;
import fourtalking.Nateam.game.dto.GameRegisterDTO;
import fourtalking.Nateam.game.entity.Game;
import fourtalking.Nateam.game.repositroy.GameRepository;
import fourtalking.Nateam.global.exception.game.GameNotFoundException;
import fourtalking.Nateam.global.exception.user.NoAuthorizationException;
import fourtalking.Nateam.user.entity.User;
import fourtalking.Nateam.user.service.UserService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final UserService userService;

    private final GameRepository gameRepository;

    public Game findById(Long gameId) {

        return gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
    }

    public GameRegisterDTO.Response registerGame(GameRegisterDTO.Request gameRequestDTO, Long userId) {

        Game savedGame = gameRepository.save(gameRequestDTO.toEntity(userId));
        User user = userService.findById(userId);
        double defaultReviewRank = 0;

        return GameRegisterDTO.Response.of(savedGame, defaultReviewRank, user.getUserName());
    }

    public GameGetDTO getGameById(Long gameId) {

        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
        User user = userService.findById(game.getUserId());
        double gameReviewRank = gameRepository.getGameReviewRank(game.getGameId());

        return GameGetDTO.of(game, gameReviewRank, user.getUserName());
    }

    public List<GameGetDTO> gameGetDTOs() {
        return gameRepository.findAllGameGetDTOOrderByCreatedTime();
    }

    @Transactional
    public GameModifyDTO.Response modifyGame(
            Long gameId,
            GameModifyDTO.Request gameModifyRequestDTO,
            Long userId) {

        Game game = findById(gameId);
        User user = userService.findById(game.getUserId());
        validateModifyGameAuthorization(userId, user.getUserId());

        modifyGame(game, gameModifyRequestDTO);

        double gameReviewRank = gameRepository.getGameReviewRank(gameId);

        return GameModifyDTO.Response.of(game, gameReviewRank, user.getUserName());
    }

    private void validateModifyGameAuthorization(Long userIdFromLoginUser, Long userIdFromGame) {

        if (!Objects.equals(userIdFromLoginUser, userIdFromGame)){
            throw new NoAuthorizationException();
        }
    }

    private void modifyGame(Game game, GameModifyDTO.Request gameModifyRequestDTO) {

        String gameName = gameModifyRequestDTO.gameName();
        String gameIntroduction = gameModifyRequestDTO.gameIntroduction();
        int gamePrice = gameModifyRequestDTO.gamePrice();

        game.modifyGame(gameName, gameIntroduction, gamePrice);
    }

    public void deleteGame(Long gameId, Long userId) {

        Game game = findById(gameId);
        validateModifyGameAuthorization(userId, game.getUserId());

        gameRepository.deleteById(gameId);
    }
}
