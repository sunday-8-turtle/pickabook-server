package beside.sunday8turtle.pickabookserver.modules.notification.service;

import beside.sunday8turtle.pickabookserver.common.exception.EntityNotFoundException;
import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import beside.sunday8turtle.pickabookserver.modules.bookmark.service.BookmarkService;
import beside.sunday8turtle.pickabookserver.modules.notification.domain.Notification;
import beside.sunday8turtle.pickabookserver.modules.notification.dto.NotificationPostResponseDTO;
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
    public void createNewNotification(NotificationPostResponseDTO dto) {
        Bookmark bookmark = bookmarkService.getBookmarkByBookmarkId(dto.getBookmarkId());
        Notification notification = notificationRepository.findFirstByBookmarkId(dto.getBookmarkId());
        if(notification == null) {
            notificationRepository.save(Notification.of(dto.getNotiType(), dto.getNotidate(), dto.getMessage(), dto.getUrl(), bookmark, bookmark.getUser()));
        }else {
            notification.update(dto.getNotiType(), dto.getNotidate(), dto.getMessage(), dto.getUrl(), bookmark, bookmark.getUser());
        }
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

    @Transactional(readOnly = true)
    public List<Notification> getNotificationsByUserId(long userId) {
        return userService.getUserById(userId)
                .map(user -> notificationRepository.findAllByUserId(user.getId()))
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void updateNotificationCheck(long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(EntityNotFoundException::new);
        notification.checkNotification();
    }
}
