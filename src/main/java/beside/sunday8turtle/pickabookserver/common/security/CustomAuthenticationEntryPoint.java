package beside.sunday8turtle.pickabookserver.common.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    에러 상황 : Jwt 토큰 없이 api 호출, 형식 맞지 않거나 만료된 토큰으로 호출
    생성 이유 : SpringSecurity는 Spring 앞단에서 필터링 처리한다. ControllerAdvice 영역까지 리퀘스트가 도달하지 않아 커스텀 예외처리 적용이 안된다.
    적용 : AuthenticationEntryPoint를 상속받아 예외 발생 시 포워딩하여 에러를 처리한다.
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException,
            ServletException {
        response.sendRedirect("/exception/entrypoint");
    }
}
