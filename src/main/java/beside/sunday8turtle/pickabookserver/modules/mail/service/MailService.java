package beside.sunday8turtle.pickabookserver.modules.mail.service;

import beside.sunday8turtle.pickabookserver.modules.user.dto.UserCertificationRequestDTO;
import beside.sunday8turtle.pickabookserver.modules.user.dto.UserSignUpRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    public void joinCompleteMailSend(UserSignUpRequestDTO dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getEmail());
        message.setSubject(dto.getNickname() + "님, 피카북(PEEK A BOOK)의 회원가입을 축하합니다.");
        message.setText(dto.getNickname() + "님, 피카북에 오신 것을 환영합니다! 피카북을 통해 즐겨찾기 항목을 편하게 관리하세요 :)");
        mailSender.send(message);
    }

    public void certificationCodeSend(UserCertificationRequestDTO dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getEmail());
        message.setSubject("피카북(PEEK A BOOK) 회원가입 인증번호 발송");
        message.setText("인증번호는 [ " + dto.getCertificationCode() + " ] 입니다. 해당 인증번호를 기입하여 회원가입을 완료해주세요.");
        mailSender.send(message);
    }

}