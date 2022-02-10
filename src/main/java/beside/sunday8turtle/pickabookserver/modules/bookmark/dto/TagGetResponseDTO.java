package beside.sunday8turtle.pickabookserver.modules.bookmark.dto;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Tag;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TagGetResponseDTO {

    private Long tagId;
    private String tagName;

    public static TagGetResponseDTO fromTag(Tag tag) {
        return new TagGetResponseDTO(
                tag.getId(),
                tag.getTagName()
        );
    }

    public static List<TagGetResponseDTO> fromTags(Page<Tag> tags) {
        return tags.map(TagGetResponseDTO::fromTag)
                .stream().collect(toList());
    }
}
