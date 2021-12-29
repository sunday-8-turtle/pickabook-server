package beside.sunday8turtle.pickabookserver.modules.user.controller;

import beside.sunday8turtle.pickabookserver.config.jwt.JwtTokenProvider;
import beside.sunday8turtle.pickabookserver.modules.user.domain.User;
import beside.sunday8turtle.pickabookserver.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody User user) {
        userService.registerUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity login(@RequestBody User user) {
        User beforeUser = userService.getUserByEmail(user.getEmail());
        String accessToken = "";

        if(beforeUser == null) return null; // TODO: getUserByEmail 내 익셉션 처리

        if(!passwordEncoder.matches(user.getPassword(), beforeUser.getPassword())) return null; // TODO: 익셉션 처리
        accessToken = jwtTokenProvider.createToken(String.valueOf(beforeUser.getEmail()), Arrays.asList("ROLE_USER"));

        Map<String, String> result = new HashMap<>();
        result.put("token", accessToken);
        result.put("expireTime", jwtTokenProvider.getExpireDate(accessToken));

        if(accessToken.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
