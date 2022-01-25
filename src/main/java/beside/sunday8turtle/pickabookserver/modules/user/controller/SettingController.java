package beside.sunday8turtle.pickabookserver.modules.user.controller;

import beside.sunday8turtle.pickabookserver.common.response.CommonResponse;
import beside.sunday8turtle.pickabookserver.modules.user.PrincipalDetails;
import beside.sunday8turtle.pickabookserver.modules.user.dto.setting.NotiPostRequestDTO;
import beside.sunday8turtle.pickabookserver.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
