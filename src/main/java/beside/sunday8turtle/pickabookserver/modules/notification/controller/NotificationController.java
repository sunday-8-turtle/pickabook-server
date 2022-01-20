package beside.sunday8turtle.pickabookserver.modules.notification.controller;

import beside.sunday8turtle.pickabookserver.modules.notification.domain.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

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
}
