package beside.sunday8turtle.pickabookserver.modules.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserCertificationRequestDTO {
    private String email;
    private String certificationCode;
}