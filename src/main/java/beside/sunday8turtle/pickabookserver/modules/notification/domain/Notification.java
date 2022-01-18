package beside.sunday8turtle.pickabookserver.modules.notification.domain;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Notification {

    public static Map<Long, SseEmitter> CLIENTS = new ConcurrentHashMap<>();

}
