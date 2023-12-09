package fourtalking.Nateam.user.controller;

import fourtalking.Nateam.global.security.userdetails.UserDetailsImpl;
import fourtalking.Nateam.user.dto.ChangePasswordDTO;
import fourtalking.Nateam.user.dto.EditProfileDTO;
import fourtalking.Nateam.user.dto.LoginDTO;
import fourtalking.Nateam.user.dto.SignupDTO;
import fourtalking.Nateam.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ResponseEntity<String> signup(@Valid @RequestBody SignupDTO signRequestDTO) {

        userService.signup(signRequestDTO);

        return ResponseEntity.ok().body("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO.Request loginRequestDTO, HttpServletResponse response) {

        userService.login(loginRequestDTO, response);

        return ResponseEntity.ok().body("로그인 성공");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {

        userService.logout(response);

        return ResponseEntity.ok().body("로그아웃 성공");
    }

    @PatchMapping("/profile")
    public ResponseEntity<EditProfileDTO.Response> editProfile(@Valid @RequestBody EditProfileDTO.Request editProfileRequestDTO, @AuthenticationPrincipal
    UserDetailsImpl userDetails) {

        EditProfileDTO.Response editProfileResponseDTO = userService.editProfile(
                editProfileRequestDTO, userDetails.getUser().getUserId());

        return ResponseEntity.ok(editProfileResponseDTO);
    }

    @PatchMapping("/password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        userService.changePassword(changePasswordDTO, userDetails.getUser().getUserId());

        return ResponseEntity.ok().body("비밀번호 변경 성공");
    }
}
