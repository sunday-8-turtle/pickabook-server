package beside.sunday8turtle.pickabookserver.modules.user.controller;

import beside.sunday8turtle.pickabookserver.config.jwt.JwtTokenProvider;
import beside.sunday8turtle.pickabookserver.modules.user.domain.User;
import beside.sunday8turtle.pickabookserver.modules.user.dto.UserPostRequestDTO;
import beside.sunday8turtle.pickabookserver.modules.user.dto.UserPostResponseDTO;
import beside.sunday8turtle.pickabookserver.modules.user.dto.UserSignUpRequestDTO;
import beside.sunday8turtle.pickabookserver.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserSignUpRequestDTO dto) {
        userService.registerUser(dto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity login(@RequestBody UserPostRequestDTO dto) {
        User beforeUser = userService.getUserByEmail(dto.getEmail());
        String accessToken = "";

        if (beforeUser == null) return null; // TODO: getUserByEmail 내 익셉션 처리

        if (!passwordEncoder.matches(dto.getPassword(), beforeUser.getPassword()))
            return null; // TODO: 익셉션 처리
        accessToken = jwtTokenProvider.createToken(String.valueOf(beforeUser.getEmail()), Arrays.asList("ROLE_USER"));

        UserPostResponseDTO userSignUpResponse = new UserPostResponseDTO();
        userSignUpResponse.setToken(accessToken);
        userSignUpResponse.setExpireTime(jwtTokenProvider.getExpireDate(accessToken));

        if (accessToken.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(userSignUpResponse, HttpStatus.OK);
    }

}
