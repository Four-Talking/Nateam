package fourtalking.Nateam.game.service;

import fourtalking.Nateam.game.dto.GameGetDTO;
import fourtalking.Nateam.game.dto.GameRegisterDTO;
import fourtalking.Nateam.game.entity.Game;
import fourtalking.Nateam.game.repositroy.GameRepository;
import fourtalking.Nateam.global.exception.game.GameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public Game findById(Long gameId) {

        return gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
    }

    public GameRegisterDTO.Response registerGame(GameRegisterDTO.Request gameRequestDTO) {

        Game savedGame = gameRepository.save(gameRequestDTO.toEntity());
        double gameReviewRank = 5.00;

        return GameRegisterDTO.Response.of(savedGame, gameReviewRank);
    }

    public GameGetDTO getGameById(Long gameId) {

        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);

        return GameGetDTO.of(game, 4.1, "손창현");
    }
}
