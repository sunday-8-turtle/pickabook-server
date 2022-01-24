package beside.sunday8turtle.pickabookserver.modules.tag.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TagPostRequestDTO {
    private Long id;
    private String tagName;
}
