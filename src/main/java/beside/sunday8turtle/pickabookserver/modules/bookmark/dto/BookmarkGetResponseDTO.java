package beside.sunday8turtle.pickabookserver.modules.bookmark.dto;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.BookmarkTag;
import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Tag;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkGetResponseDTO {
    private Long bookmarkId;
    private String title;
    private String url;
    private String description;
    private String image;
    private List<String> tags;
    private LocalDate notidate;
    private LocalDate createdDate;

    public static BookmarkGetResponseDTO fromBookmark(Bookmark bookmark) {
        return new BookmarkGetResponseDTO(
                bookmark.getId(),
                bookmark.getTitle(),
                bookmark.getUrl(),
                bookmark.getDescription(),
                bookmark.getImage(),
                bookmark.getBookmarkTags().stream()
                        .map(BookmarkTag::getTag)
                        .map(Tag::getTagName)
                        .collect(toList()),
                bookmark.getNotidate(),
                bookmark.getCreatedDate()
        );
    }

    public static List<BookmarkGetResponseDTO> fromBookmarks(Page<Bookmark> bookmarks) {
        return bookmarks.map(BookmarkGetResponseDTO::fromBookmark)
                .stream().collect(toList());
    }
}
