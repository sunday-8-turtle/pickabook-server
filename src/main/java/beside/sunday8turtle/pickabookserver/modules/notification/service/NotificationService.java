package beside.sunday8turtle.pickabookserver.modules.notification.service;

import beside.sunday8turtle.pickabookserver.common.exception.EntityNotFoundException;
import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import beside.sunday8turtle.pickabookserver.modules.bookmark.service.BookmarkService;
import beside.sunday8turtle.pickabookserver.modules.notification.domain.Notification;
import beside.sunday8turtle.pickabookserver.modules.notification.repository.NotificationRepository;
import beside.sunday8turtle.pickabookserver.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final BookmarkService bookmarkService;
    private final UserService userService;

    @Transactional
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

    @Transactional
    public void createNewNotifications(List<Bookmark> bookmarks) {
        for (Bookmark bookmark : bookmarks) {
            Notification notification = Notification.of("BROWSER", bookmark.getNotidate(), "까꿍~! 북마크 예약 알림입니다.", "url", bookmark, bookmark.getUser());
            notificationRepository.save(notification);
        }

    }

    @Transactional
    public void createNewNotificationByBookmarkId(long bookmarkId) {
        Bookmark bookmark = bookmarkService.getBookmarkByBookmarkId(bookmarkId);
        Notification notification = Notification.of("BROWSER", bookmark.getNotidate(), "까꿍~! 북마크 테스트 알림입니다.", "url", bookmark, bookmark.getUser());
        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public Notification getNotificationByNotificatonId(long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Page<Notification> getNotificationsByUserId(long userId, Pageable pageable) {
        return userService.getUserById(userId)
                .map(user -> notificationRepository.findAllByUserId(user.getId(), pageable))
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void updateNotificationCheck(long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(EntityNotFoundException::new);
        notification.checkNotification();
    }

}
