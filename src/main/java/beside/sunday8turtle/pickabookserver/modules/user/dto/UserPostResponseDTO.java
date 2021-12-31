package beside.sunday8turtle.pickabookserver.modules.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserPostResponseDTO {
    private String token;
    private String expireTime;
}
