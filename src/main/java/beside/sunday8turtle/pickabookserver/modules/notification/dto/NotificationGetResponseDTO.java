package beside.sunday8turtle.pickabookserver.modules.notification.dto;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkGetResponseDTO;
import beside.sunday8turtle.pickabookserver.modules.notification.domain.Notification;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NotificationGetResponseDTO {
    private long id;
    private String notiType;
    private LocalDate notidate;
    private String message;
    private String url;
    private boolean check;
    private BookmarkGetResponseDTO bookmark;

    public static NotificationGetResponseDTO fromNotification(Notification notification) {
        return new NotificationGetResponseDTO(
                notification.getId(),
                notification.getNotiType(),
                notification.getNotidate(),
                notification.getMessage(),
                notification.getUrl(),
                notification.isCheck(),
                BookmarkGetResponseDTO.fromBookmark(notification.getBookmark())
        );
    }

    public static List<NotificationGetResponseDTO> fromNotifications(Page<Notification> notifications) {
        return notifications.map(NotificationGetResponseDTO::fromNotification)
                .stream().collect(Collectors.toList());
    }
}
