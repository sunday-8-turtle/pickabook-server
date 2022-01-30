package beside.sunday8turtle.pickabookserver.modules.user.controller;

import beside.sunday8turtle.pickabookserver.common.exception.IllegalStatusException;
import beside.sunday8turtle.pickabookserver.common.exception.InvalidParamException;
import beside.sunday8turtle.pickabookserver.common.response.CommonResponse;
import beside.sunday8turtle.pickabookserver.common.response.ErrorCode;
import beside.sunday8turtle.pickabookserver.common.security.CustomSecurityException;
import beside.sunday8turtle.pickabookserver.common.util.RedisUtil;
import beside.sunday8turtle.pickabookserver.config.jwt.JwtTokenProvider;
import beside.sunday8turtle.pickabookserver.modules.mail.service.MailService;
import beside.sunday8turtle.pickabookserver.modules.user.domain.User;
import beside.sunday8turtle.pickabookserver.modules.user.dto.*;
import beside.sunday8turtle.pickabookserver.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;
    private final MailService mailService;

    @PostMapping("/signup")
    public CommonResponse signup(@RequestBody UserSignUpRequestDTO dto) {
        userService.getUserByEmail(dto.getEmail()).ifPresent(m -> { throw new IllegalStatusException("이미 존재하는 회원입니다."); });
        userService.registerUser(dto);
        mailService.joinCompleteMailSend(dto); // 회원가입 완료 메일 발송
        return CommonResponse.success();
    }

    @PostMapping("")
    public CommonResponse<UserPostResponseDTO> login(@RequestBody UserPostRequestDTO dto) {
        Optional<User> user = userService.getUserByEmailAndPassword(dto.getEmail(), dto.getPassword());
        user.orElseThrow(() -> new InvalidParamException());
        String accessToken = jwtTokenProvider.generateToken(String.valueOf(user.get().getEmail()), user.get().getRoleList());
        String refreshToken = jwtTokenProvider.generateRefreshToken(String.valueOf(user.get().getEmail()), user.get().getRoleList());
        redisUtil.setValues(user.get().getEmail(), refreshToken, JwtTokenProvider.refreshTokenValidSecond);
        return CommonResponse.success(new UserPostResponseDTO(accessToken, jwtTokenProvider.getExpireDate(accessToken), refreshToken));
    }

    @PostMapping("/reissue")
    public CommonResponse<UserPostResponseDTO> reissue(@RequestBody TokenRequestDTO dto) {
        String userPk = jwtTokenProvider.getUserPk(dto.getAccessToken());
        if(!redisUtil.hasValues(userPk) || !dto.getRefreshToken().equals(redisUtil.getValues(userPk))) {
            throw new CustomSecurityException(ErrorCode.AUTH_INVALID_REFRESH_TOKEN);
        }
        String reissueToken = jwtTokenProvider.generateToken(userPk, Arrays.asList("ROLE_USER"));
        return CommonResponse.success(
                new UserPostResponseDTO(
                        reissueToken,
                        jwtTokenProvider.getExpireDate(reissueToken),
                        dto.getRefreshToken()));
    }

    @DeleteMapping("/logout")
    public CommonResponse logout(@RequestBody TokenRequestDTO dto) {
        String userPk = jwtTokenProvider.getUserPk(dto.getAccessToken());
        if(redisUtil.hasValues(userPk)) {
            redisUtil.delValues(userPk);
        }

        return CommonResponse.success();
    }

    @PostMapping("/email/send")
    public CommonResponse emailCodeSend(@RequestBody UserCertificationRequestDTO dto) {
        String emailCode = userService.generateEmailCode(dto.getEmail());
        dto.setCertificationCode(emailCode);
        mailService.certificationCodeSend(dto);
        return CommonResponse.success();
    }

    @PostMapping("/email/certification")
    public CommonResponse emailCodeCertification(@RequestBody UserCertificationRequestDTO dto) {
        userService.certificationCode(dto.getEmail(), dto.getCertificationCode());
        return CommonResponse.success();
    }

}
