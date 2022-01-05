package beside.sunday8turtle.pickabookserver.modules.user.controller;

import beside.sunday8turtle.pickabookserver.common.exception.IllegalStatusException;
import beside.sunday8turtle.pickabookserver.common.exception.InvalidParamException;
import beside.sunday8turtle.pickabookserver.common.response.CommonResponse;
import beside.sunday8turtle.pickabookserver.common.util.RedisUtil;
import beside.sunday8turtle.pickabookserver.config.jwt.JwtTokenProvider;
import beside.sunday8turtle.pickabookserver.modules.user.domain.User;
import beside.sunday8turtle.pickabookserver.modules.user.dto.UserPostRequestDTO;
import beside.sunday8turtle.pickabookserver.modules.user.dto.UserPostResponseDTO;
import beside.sunday8turtle.pickabookserver.modules.user.dto.UserSignUpRequestDTO;
import beside.sunday8turtle.pickabookserver.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    @PostMapping("/signup")
    public CommonResponse signup(@RequestBody UserSignUpRequestDTO dto) {
        userService.getUserByEmail(dto.getEmail()).ifPresent(m -> { throw new IllegalStatusException("이미 존재하는 회원입니다."); });
        userService.registerUser(dto);
        return CommonResponse.success();
    }

    @PostMapping("")
    public CommonResponse<UserPostResponseDTO> login(@RequestBody UserPostRequestDTO dto) {
        Optional<User> user = userService.getUserByEmailAndPassword(dto.getEmail(), dto.getPassword());
        user.orElseThrow(() -> new InvalidParamException());
        String accessToken = jwtTokenProvider.generateToken(String.valueOf(user.get().getEmail()), user.get().getRoleList());
        String refreshToken = jwtTokenProvider.generateRefreshToken(String.valueOf(user.get().getEmail()), user.get().getRoleList());
        redisUtil.setValues(refreshToken, user.get().getEmail(), JwtTokenProvider.refreshTokenValidSecond);
        return CommonResponse.success(new UserPostResponseDTO(accessToken, jwtTokenProvider.getExpireDate(accessToken), refreshToken));
    }

}
