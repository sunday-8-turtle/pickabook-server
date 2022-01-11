package beside.sunday8turtle.pickabookserver.common.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    에러 상황 : Jwt 토큰으로 API 호출 하였으나 해당 리소스 권한 없는 경우
    적용 : AccessDeniedHandler 상속받아 예외 발생 시 포워딩하여 에러를 처리한다.
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.sendRedirect("/exception/accessdenied");
    }
}
