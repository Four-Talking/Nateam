package fourtalking.Nateam.game.controller;

import fourtalking.Nateam.game.dto.GameRegisterDTO;
import fourtalking.Nateam.game.dto.GameRegisterDTO.Response;
import fourtalking.Nateam.game.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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



}
