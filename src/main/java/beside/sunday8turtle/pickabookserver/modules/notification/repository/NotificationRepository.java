package beside.sunday8turtle.pickabookserver.modules.notification.repository;

import beside.sunday8turtle.pickabookserver.modules.notification.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findAllByUserId(long userId, Pageable pageable);

    Notification findFirstByBookmarkId(long bookmarkId);
}
