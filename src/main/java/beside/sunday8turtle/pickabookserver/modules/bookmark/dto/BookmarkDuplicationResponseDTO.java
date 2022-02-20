package beside.sunday8turtle.pickabookserver.modules.bookmark.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDuplicationResponseDTO {
    private boolean duplication;

    public static BookmarkDuplicationResponseDTO from(boolean isDuplication) {
        return new BookmarkDuplicationResponseDTO(isDuplication);
    }
}
