package fourtalking.Nateam.cartgame.controller;

import fourtalking.Nateam.cartgame.dto.CartGameRegisterDTO;
import fourtalking.Nateam.cartgame.dto.CartUpdateDTO;
import fourtalking.Nateam.cartgame.dto.CartsGetDTO;
import fourtalking.Nateam.cartgame.service.CartGameService;
import fourtalking.Nateam.global.security.userdetails.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

  // 장바구니 수정
  @PutMapping("/{gameId}")
  public ResponseEntity<CartUpdateDTO> updateCartGame(
      @PathVariable Long gameId,
      @RequestBody @Valid CartGameRegisterDTO RequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {

    CartUpdateDTO cartUpdateDTO = cartGameService.updateCartGame(gameId, RequestDto.orderCount(),
        userDetails.getUser().getUserId());

    return ResponseEntity.ok(cartUpdateDTO);
  }

  // 장바구니 조회
  @GetMapping()
  public ResponseEntity<CartsGetDTO> getCartsGame(
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {

    CartsGetDTO cartsGetDTO = cartGameService.getCartsGame(userDetails.getUser().getUserId());

    return ResponseEntity.ok(cartsGetDTO);

  }

  // 장바구니 삭제
  @DeleteMapping("/{gameId}")
  public ResponseEntity<CartsGetDTO> deleteCartGame(
      @PathVariable Long gameId,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {

    CartsGetDTO cartsGetDTO = cartGameService.deleteCartGame(gameId, userDetails.getUser().getUserId());

    return ResponseEntity.ok(cartsGetDTO);
  }


}
