package beside.sunday8turtle.pickabookserver.modules.user.controller;

import beside.sunday8turtle.pickabookserver.common.response.CommonResponse;
import beside.sunday8turtle.pickabookserver.modules.user.PrincipalDetails;
import beside.sunday8turtle.pickabookserver.modules.user.domain.User;
import beside.sunday8turtle.pickabookserver.modules.user.dto.setting.NotiPostRequestDTO;
import beside.sunday8turtle.pickabookserver.modules.user.dto.setting.SettingNicknameRequestDTO;
import beside.sunday8turtle.pickabookserver.modules.user.dto.setting.SettingPasswordRequestDTO;
import beside.sunday8turtle.pickabookserver.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/setting")
public class SettingController {

    private final UserService userService;

    @PostMapping("/noti/email")
    public CommonResponse updateNotiEmail(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody NotiPostRequestDTO dto) {
        long userId = principalDetails.getUser().getId();
        userService.updateNotiEmail(userId, dto.isNoti());
        return CommonResponse.success();
    }

    @PostMapping("/noti/browser")
    public CommonResponse updateNotiBrowser(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody NotiPostRequestDTO dto) {
        long userId = principalDetails.getUser().getId();
        userService.updateNotiBrowser(userId, dto.isNoti());
        return CommonResponse.success();
    }

    @PutMapping("/password")
    public CommonResponse updatePassword(@RequestBody SettingPasswordRequestDTO dto) {
        User user = userService.getUserByEmail(dto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));
        userService.updatePassword(user.getId(), dto.getPassword());
        return CommonResponse.success();
    }

    @PutMapping("/nickname")
    public CommonResponse updateNickname(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody SettingNicknameRequestDTO dto) {
        long userId = principalDetails.getUser().getId();
        userService.updateNickname(userId, dto.getNickname());
        return CommonResponse.success();
    }

}
