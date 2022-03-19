package beside.sunday8turtle.pickabookserver.modules.bookmark.dto;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkPostResponseDTO {
    private Long bookmarkId;

    public static BookmarkPostResponseDTO fromBookmark(Bookmark bookmark) {
        return new BookmarkPostResponseDTO(
                bookmark.getId()
        );
    }
}
