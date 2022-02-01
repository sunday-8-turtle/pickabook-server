package beside.sunday8turtle.pickabookserver.modules.notification.domain;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
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
public class Notification {

    public static Map<Long, SseEmitter> CLIENTS = new ConcurrentHashMap<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "bookmark_id")
    private Bookmark bookmark;

    private String notiType;
    private LocalDate notidate;
    private String message;
    private String url;
    private boolean isCheck;

    private Notification(String notiType, LocalDate notidate, String message, String url, Bookmark bookmark) {
        this.notiType = notiType;
        this.notidate = notidate;
        this.message = message;
        this.url = url;
        this.bookmark = bookmark;
    }

    protected Notification() {
    }

    public static Notification of(String notiType, LocalDate notidate, String message, String url, Bookmark bookmark) {
        return new Notification(notiType, notidate, message, url, bookmark);
    }
}
