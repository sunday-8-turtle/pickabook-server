package beside.sunday8turtle.pickabookserver.modules.bookmark.dto;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import beside.sunday8turtle.pickabookserver.modules.bookmarktag.domain.BookmarkTag;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkPutResponseDTO {
        private String title;
        private String url;
        private String description;
        private List<BookmarkTag> bookmarkTagList;
        private LocalDate notidate;

        public static BookmarkPutResponseDTO fromBookmark(Bookmark bookmark) {
                return new BookmarkPutResponseDTO(
                        bookmark.getTitle(),
                        bookmark.getUrl(),
                        bookmark.getDescription(),
                        bookmark.getBookmarkTags(),
                        bookmark.getNotidate()
                );
        }
}
