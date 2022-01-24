package beside.sunday8turtle.pickabookserver.modules.bookmark.domain;

import beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkUpdateRequest;
import beside.sunday8turtle.pickabookserver.modules.bookmarktag.domain.BookmarkTag;
import beside.sunday8turtle.pickabookserver.modules.user.domain.User;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String url;
    private String description;
    private LocalDate notidate;
    @OneToMany(mappedBy = "bookmark", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<BookmarkTag> bookmarkTags = new ArrayList<>();
    //TODO: 알림유무 필드 추가 예정
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Bookmark(String title, String url, String description, LocalDate notidate, User user) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.notidate = notidate;
        this.user = user;
    }

    protected Bookmark() {
    }

    public static Bookmark of(String title, String url, String description, LocalDate notidate, User user) {
        return new Bookmark(title, url, description, notidate, user);
    }

    public Bookmark updateBookmark(Bookmark bookmark, BookmarkUpdateRequest updateRequest) {
        updateRequest.getTitleToUpdate().ifPresent(titleToUpdate -> title = titleToUpdate);
        updateRequest.getUrlToUpdate().ifPresent(urlToUpdate -> url = urlToUpdate);
        updateRequest.getDescriptionToUpdate().ifPresent(descriptionToUpdate -> description = descriptionToUpdate);
        updateRequest.getBookmarkTagsToUpdate().ifPresent(bookmarkTagsToUpdate -> bookmarkTags = bookmarkTagsToUpdate);
        updateRequest.getNotidateToUpdate().ifPresent(notidateToUpdate -> notidate = notidateToUpdate);
        return bookmark;
    }

    public void setBookmarkTag(List<BookmarkTag> bookmarkTags) {
        this.bookmarkTags = bookmarkTags;
    }
}
