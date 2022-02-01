package beside.sunday8turtle.pickabookserver.modules.notification.repository;

import beside.sunday8turtle.pickabookserver.modules.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
