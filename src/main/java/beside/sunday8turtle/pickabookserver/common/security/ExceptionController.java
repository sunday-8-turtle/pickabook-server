package beside.sunday8turtle.pickabookserver.common.security;

import beside.sunday8turtle.pickabookserver.common.exception.BaseException;
import beside.sunday8turtle.pickabookserver.common.response.ErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/entrypoint")
    public void entrypointException() {
        throw new CustomSecurityException(ErrorCode.AUTH_INVALID_TOKEN);
    }

    @GetMapping("/accessdenied")
    public void accessDeniedException() {
        throw new CustomSecurityException(ErrorCode.AUTH_FORBIDDEN);
    }

}
