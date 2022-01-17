package beside.sunday8turtle.pickabookserver.modules.bookmark.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkPostRequestDTO {
        private String title;
        private String url;
        private String description;
        private String tag;
        private LocalDate notidate;
}
