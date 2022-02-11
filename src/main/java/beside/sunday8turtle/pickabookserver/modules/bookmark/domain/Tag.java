package beside.sunday8turtle.pickabookserver.modules.bookmark.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String tagName;
    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<BookmarkTag> bookmarkTagList = new ArrayList<>();

    private Tag(String tagName) {
        this.tagName = tagName;
    }
    protected Tag() {
    }

    public static Tag of(String tagName) {
        return new Tag(tagName);
    }

}
