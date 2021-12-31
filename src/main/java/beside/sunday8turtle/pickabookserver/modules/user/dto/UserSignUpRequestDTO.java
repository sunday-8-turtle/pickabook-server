package beside.sunday8turtle.pickabookserver.modules.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequestDTO {
    private  String email;
    private  String password;
    private  String nickname;
}
