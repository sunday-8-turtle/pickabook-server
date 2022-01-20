package beside.sunday8turtle.pickabookserver.modules.bookmark.dto;

import lombok.*;

import java.time.LocalDate;

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
        private String tag;
        private LocalDate notidate;

        public BookmarkUpdateRequest toUpdateRequest() {
                return builder().titleToUpdate(title)
                        .urlToUpdate(url)
                        .descriptionToUpdate(description)
                        .tagToUpdate(tag)
                        .notidateToUpdate(notidate)
                        .build();
        }

}
