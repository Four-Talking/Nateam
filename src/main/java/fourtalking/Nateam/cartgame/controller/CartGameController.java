package fourtalking.Nateam.cartgame.controller;

import fourtalking.Nateam.cartgame.service.CartGameService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/carts")
@RestController
@RequiredArgsConstructor
public class CartGameController {

  private final CartGameService cartGameService;

  @PutMapping
  public ResponseEntity<List<?>> updateCartGame(@RequestBody ) {
    List<?> cardGameResponseDto = cartGameService.updateCartGame();

    return ResponseEntity.ok(cardGameResponseDto);
  }


}
