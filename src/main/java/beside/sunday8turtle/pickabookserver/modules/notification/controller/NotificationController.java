package beside.sunday8turtle.pickabookserver.modules.notification.controller;

import beside.sunday8turtle.pickabookserver.common.response.CommonResponse;
import beside.sunday8turtle.pickabookserver.modules.notification.domain.Notification;
import beside.sunday8turtle.pickabookserver.modules.notification.dto.NotificationGetResponseDTO;
import beside.sunday8turtle.pickabookserver.modules.notification.dto.NotificationPostResponseDTO;
import beside.sunday8turtle.pickabookserver.modules.notification.service.NotificationService;
import beside.sunday8turtle.pickabookserver.modules.user.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/subscribe/{id}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable long id) {

        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        try {
            emitter.send("connection success!", MediaType.APPLICATION_JSON);
        }catch (Exception e) {
            log.warn("disconnected id : {}", id);
        }

        Notification.CLIENTS.put(id, emitter);

        emitter.onTimeout(() -> Notification.CLIENTS.remove(id));
        emitter.onCompletion(() -> Notification.CLIENTS.remove(id));
        emitter.onError((e) -> Notification.CLIENTS.remove(id));

        return emitter;
    }

    // test용
    @GetMapping("/publish")
    public void publish() {
        Set<Long> deadIds = new HashSet<>();

        Notification.CLIENTS.forEach((id, emitter) -> {
            try {
                emitter.send("까꿍~! 즐겨찾기 링크를 확인하세요!!", MediaType.APPLICATION_JSON);
            } catch (Exception e) {
                deadIds.add(id);
                log.warn("disconnected id : {}", id);
            }
        });

        deadIds.forEach(Notification.CLIENTS::remove);
    }

    // Notification 생성 테스트
    @PostMapping("")
    public CommonResponse createNotification(@RequestBody NotificationPostResponseDTO dto) {
        notificationService.createNewNotification(dto);
        return CommonResponse.success();
    }

    @PostMapping("/check/{notificationId}")
    public CommonResponse checkNotification(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long notificationId) {
        // TODO: Auth User와 Notification User가 동일한지 체크
        notificationService.updateNotificationCheck(notificationId);
        return CommonResponse.success();
    }

    @GetMapping("/{notificationId}")
    public CommonResponse<NotificationGetResponseDTO> getNotification(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long notificationId) {
        // TODO: Auth User와 Notification User가 동일한지 체크
        return CommonResponse.success(NotificationGetResponseDTO.fromNotification(notificationService.getNotificationByNotificatonId(notificationId)));
    }

    @GetMapping("")
    public CommonResponse<List<NotificationGetResponseDTO>> getNotifications(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        long userId = principalDetails.getUser().getId();
        if(page != null && size != null) {
            return CommonResponse.success(NotificationGetResponseDTO.fromNotifications(notificationService.getNotificationsByUserId(userId, PageRequest.of(page, size, Sort.Direction.DESC, "id"))));
        }else {
            return CommonResponse.success(NotificationGetResponseDTO.fromNotifications(notificationService.getNotificationsByUserId(userId)));
        }
    }

}
