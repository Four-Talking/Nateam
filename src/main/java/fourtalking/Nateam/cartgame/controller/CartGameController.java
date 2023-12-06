package fourtalking.Nateam.cartgame.controller;

import fourtalking.Nateam.cartgame.dto.CartGameRegisterDTO;
import fourtalking.Nateam.cartgame.dto.CartGameRegisterDTO.CartResponse;
import fourtalking.Nateam.cartgame.service.CartGameService;
import fourtalking.Nateam.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/carts")
@RestController
@RequiredArgsConstructor
public class CartGameController {

  private final CartGameService cartGameService;

  @PutMapping("/{gameId}")
  public ResponseEntity<List<?>> updateCartGame(
      @PathVariable Long gameId,
      @RequestBody CartGameRegisterDTO.CartRequest RequestDto
      //@AuthenticationPrincipal UserDetailsImpl userDetails
       ) {

    User user;

    List<CartResponse> cartGameResponseDto = cartGameService.updateCartGame(gameId, RequestDto,user);

    return ResponseEntity.ok(cartGameResponseDto);
  }


}
