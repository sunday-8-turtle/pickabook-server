package beside.sunday8turtle.pickabookserver.modules.user.dto;

import beside.sunday8turtle.pickabookserver.modules.user.domain.User;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserGetResponseDTO {
    private long id;
    private String email;
    private String nickname;
    private boolean browserNoti;
    private boolean emailNoti;

    public static UserGetResponseDTO fromUser(User user) {
        return new UserGetResponseDTO(
          user.getId(),
          user.getEmail(),
          user.getNickname(),
          user.isBrowserNoti(),
          user.isEmailNoti()
        );
    }
}
