package fourtalking.Nateam.user.controller;

import fourtalking.Nateam.user.dto.LoginDTO;
import fourtalking.Nateam.user.dto.SignupDTO;
import fourtalking.Nateam.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupDTO.Response> signup(@Valid @RequestBody SignupDTO.Request signRequestDTO) {

        SignupDTO.Response signupResponseDTO = userService.signup(signRequestDTO);

        return ResponseEntity.ok(signupResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO.Response> login(@RequestBody LoginDTO.Request loginRequestDTO, HttpServletResponse response) {

        LoginDTO.Response loginResponseDTO = userService.login(loginRequestDTO, response);

        return ResponseEntity.ok(loginResponseDTO);
    }
}
