package beside.sunday8turtle.pickabookserver.modules.bookmark.dto;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkPostResponseDTO {
    private String title;
    private String url;
    private String description;
    private Date notidate;

   public static BookmarkPostResponseDTO fromBookmark(Bookmark bookmark) {
        return new BookmarkPostResponseDTO(
                bookmark.getTitle(),
                bookmark.getUrl(),
                bookmark.getDescription(),
                bookmark.getNotidate()
        );
    }
}
