package beside.sunday8turtle.pickabookserver.modules.bookmark.dto;

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
        private List<String> tags;
        private LocalDate notidate;

        public BookmarkUpdateRequest toUpdateRequest() {
                return builder().titleToUpdate(title)
                        .urlToUpdate(url)
                        .descriptionToUpdate(description)
                        .notidateToUpdate(notidate)
                        .build();
        }

}
