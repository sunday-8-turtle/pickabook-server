package beside.sunday8turtle.pickabookserver.modules.bookmark.domain;

import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkUpdateRequest;
import beside.sunday8turtle.pickabookserver.modules.user.domain.User;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@ToString
@Table(name = "BOOKMARK")
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String url;
    private String description;
    private String tag; //TODO: tag 도메인 생성 예정
    private Date notidate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Bookmark(String title, String url, String description, String tag, Date notidate, User user) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.tag = tag;
        this.notidate = notidate;
        this.user = user;
    }

    protected Bookmark() {
    }

    public static Bookmark of(String title, String url, String description, String tag, Date notidate, User user) {
        return new Bookmark(title, url, description, tag, notidate, user);
    }
    

    public void updateBookmark(BookmarkUpdateRequest updateRequest) {
        updateRequest.getTitleToUpdate().ifPresent(titleToUpdate -> title = titleToUpdate);
        updateRequest.getUrlToUpdate().ifPresent(urlToUpdate -> url = urlToUpdate);
        updateRequest.getDescriptionToUpdate().ifPresent(descriptionToUpdate -> description = descriptionToUpdate);
        updateRequest.getTagToUpdate().ifPresent(tagToUpdate -> tag = tagToUpdate);
        updateRequest.getNotidateToUpdate().ifPresent(notidateToUpdate -> notidate = notidateToUpdate);
    }
}
