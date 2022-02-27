package beside.sunday8turtle.pickabookserver.modules.user.dto;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequestDTO {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private  String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{10,20}",
             message = "비밀번호는 영문, 숫자, 특수기호가 적어도 1개 이상씩 포함된 10자 ~ 20자의 비밀번호여야 합니다.")
    private  String password;

    @Size(max = 10, message = "닉네임은 10자 이하어야 합니다.")
    private  String nickname;
}
