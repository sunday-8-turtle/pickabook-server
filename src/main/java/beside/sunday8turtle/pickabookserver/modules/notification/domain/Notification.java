package beside.sunday8turtle.pickabookserver.modules.notification.domain;

import beside.sunday8turtle.pickabookserver.common.entity.BaseTimeEntity;
import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import beside.sunday8turtle.pickabookserver.modules.user.domain.User;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Getter
@ToString
@Table(name = "NOTIFICATION")
public class Notification extends BaseTimeEntity {

    public static Map<Long, SseEmitter> CLIENTS = new ConcurrentHashMap<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String notiType;
    private LocalDate notidate;
    private String message;
    private String url;
    private boolean isCheck;

    @OneToOne
    @JoinColumn(name = "bookmark_id")
    private Bookmark bookmark;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Notification(String notiType, LocalDate notidate, String message, String url, Bookmark bookmark, User user) {
        this.notiType = notiType;
        this.notidate = notidate;
        this.message = message;
        this.url = url;
        this.bookmark = bookmark;
        this.user = user;
    }

    protected Notification() {
    }

    public static Notification of(String notiType, LocalDate notidate, String message, String url, Bookmark bookmark, User user) {
        return new Notification(notiType, notidate, message, url, bookmark, user);
    }

    public void checkNotification() {
        this.isCheck = true;
    }
}
