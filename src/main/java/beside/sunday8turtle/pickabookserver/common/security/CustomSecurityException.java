package beside.sunday8turtle.pickabookserver.common.security;

import beside.sunday8turtle.pickabookserver.common.exception.BaseException;
import beside.sunday8turtle.pickabookserver.common.response.ErrorCode;

public class CustomSecurityException extends BaseException {

    public CustomSecurityException() {
        super(ErrorCode.AUTH_INVALID);
    }

    public CustomSecurityException(ErrorCode errorCode) {
        super(errorCode);
    }
}
