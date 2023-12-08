package fourtalking.Nateam.order.controller;

import fourtalking.Nateam.global.security.userdetails.UserDetailsImpl;
import fourtalking.Nateam.order.dto.OrderGetDTO;
import fourtalking.Nateam.order.dto.OrderRegisterDTO;
import fourtalking.Nateam.order.service.OrderService;
import fourtalking.Nateam.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

  private final OrderService orderService;

  @PostMapping()
  public ResponseEntity<OrderRegisterDTO> registerOrder(@AuthenticationPrincipal UserDetailsImpl userDetails){
    OrderRegisterDTO orderRegisterDTO = orderService.registerOrder(userDetails.getUser());

    return ResponseEntity.ok(orderRegisterDTO);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderGetDTO> getOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable("orderId") Long orderId){

    User user = userDetails.getUser();
    OrderGetDTO orderGetDTO = orderService.getOrder(user.getUserName(), orderId);

    return ResponseEntity.ok(orderGetDTO);
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<String> deleteOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
          @PathVariable("orderId") Long orderId){

    User user = userDetails.getUser();
    orderService.deleteOrder(user.getUserId(), orderId);

    return ResponseEntity.ok("삭제 성공");
  }
}
