package beside.sunday8turtle.pickabookserver.modules.bookmarktag.domain;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import beside.sunday8turtle.pickabookserver.modules.tag.domain.Tag;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(BookmarkTagId.class)
@Getter
public class BookmarkTag implements Serializable {

    @Id
    private Long bookmarkId;

    @Id
    private Long tagId;

    @ManyToOne(targetEntity = Bookmark.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "bookmarkId", insertable = false, updatable = false)
    private Bookmark bookmark;

    @ManyToOne(targetEntity = Tag.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "tagId", insertable = false, updatable = false)
    private Tag tag;

    private BookmarkTag(Long bookmarkId, Long tagId) {
        this.bookmarkId = bookmarkId;
        this.tagId = tagId;
    }
    protected BookmarkTag() {
    }

    public static BookmarkTag of(Long bookmarkId, Long tagId) {
        return new BookmarkTag(bookmarkId,tagId);
    }

}
