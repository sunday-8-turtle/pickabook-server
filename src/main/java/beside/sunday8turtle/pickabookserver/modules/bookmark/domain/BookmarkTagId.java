package beside.sunday8turtle.pickabookserver.modules.bookmark.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@ToString
@EqualsAndHashCode
public class BookmarkTagId implements Serializable {

    private Long bookmarkId;

    private Long tagId;

}
