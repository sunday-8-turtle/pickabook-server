package beside.sunday8turtle.pickabookserver.modules.notification.service;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import beside.sunday8turtle.pickabookserver.modules.bookmark.service.BookmarkService;
import beside.sunday8turtle.pickabookserver.modules.notification.domain.Notification;
import beside.sunday8turtle.pickabookserver.modules.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void bookmarkNotify(List<Bookmark> bookmarks) {
        for (Bookmark bookmark : bookmarks) {
            Long userId = bookmark.getUser().getId();
            if (Notification.CLIENTS.containsKey(userId)) {
                SseEmitter emitter = Notification.CLIENTS.get(userId);
                try {
                    emitter.send("까꿍~! 북마크 예약 알림입니다.", MediaType.APPLICATION_JSON);
                } catch (Exception e) {
                    Notification.CLIENTS.remove(userId);
                }
            }
        }
    }

    public void createNewNotifications(List<Bookmark> bookmarks) {
        for (Bookmark bookmark : bookmarks) {
            Notification notification = Notification.of("BROWSER", bookmark.getNotidate(), "까꿍~! 북마크 예약 알림입니다.", "url", bookmark);
            notificationRepository.save(notification);
        }

    }

}
