package beside.sunday8turtle.pickabookserver.modules.bookmark.dto;

import beside.sunday8turtle.pickabookserver.modules.bookmarktag.domain.BookmarkTag;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

import static beside.sunday8turtle.pickabookserver.modules.bookmark.dto.BookmarkUpdateRequest.builder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkPutRequestDTO {
        private String title;
        private String url;
        private String description;
        private List<BookmarkTag> bookmarkTagList;
        private LocalDate notidate;

        public BookmarkUpdateRequest toUpdateRequest() {
                return builder().titleToUpdate(title)
                        .urlToUpdate(url)
                        .descriptionToUpdate(description)
                        .bookmarkTagListToUpdate(bookmarkTagList)
                        .notidateToUpdate(notidate)
                        .build();
        }

}
