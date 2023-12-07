package fourtalking.Nateam.game.controller;

import fourtalking.Nateam.game.dto.GameGetDTO;
import fourtalking.Nateam.game.dto.GameRegisterDTO;
import fourtalking.Nateam.game.dto.GameRegisterDTO.Response;
import fourtalking.Nateam.game.service.GameService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    @PostMapping
    public ResponseEntity<?> registerGame(@RequestBody @Valid GameRegisterDTO.Request gameRequestDTO) {
        Response gameResponseDTO = gameService.registerGame(gameRequestDTO);

        return ResponseEntity.ok(gameResponseDTO);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<?> getGameById(@PathVariable Long gameId) {

        GameGetDTO gameGetDTO = gameService.getGameById(gameId);

        return ResponseEntity.ok(gameGetDTO);
    }

    @GetMapping
    public ResponseEntity<?> getGames() {

        List<GameGetDTO> gameGetDTOs = gameService.gameGetDTOs();

        return ResponseEntity.ok(gameGetDTOs);
    }

}
