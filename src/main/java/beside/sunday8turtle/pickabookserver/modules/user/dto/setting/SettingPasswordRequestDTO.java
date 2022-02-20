package beside.sunday8turtle.pickabookserver.modules.user.dto.setting;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SettingPasswordRequestDTO {
    private String beforePassword;
    private String password;
}
