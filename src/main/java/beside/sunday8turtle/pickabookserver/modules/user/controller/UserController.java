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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserSignUpRequestDTO dto) {
        userService.getUserByEmail(dto.getEmail()).ifPresent(m -> { throw new IllegalStateException("이미 존재하는 회원입니다."); });
        userService.registerUser(dto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("")
    public UserPostResponseDTO login(@RequestBody UserPostRequestDTO dto) {
        Optional<User> userByEmailAndPassword = userService.getUserByEmailAndPassword(dto.getEmail(), dto.getPassword());
        userByEmailAndPassword.orElseThrow(() -> new IllegalStateException("아이디 혹은 비밀번호가 유효하지 않습니다."));
        String accessToken = jwtTokenProvider.createToken(String.valueOf(userByEmailAndPassword.get().getEmail()), Arrays.asList("ROLE_USER"));
        return new UserPostResponseDTO(accessToken, jwtTokenProvider.getExpireDate(accessToken));
    }

}
