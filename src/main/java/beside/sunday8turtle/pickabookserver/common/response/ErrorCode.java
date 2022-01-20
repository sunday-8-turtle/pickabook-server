package beside.sunday8turtle.pickabookserver.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 공통 에러코드
    COMMON_SYSTEM_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."), // 장애 상황
    COMMON_INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    COMMON_ILLEGAL_STATUS("잘못된 상태값입니다."),

    // 인증, 권한
    AUTH_INVALID("권한이 유효하지 않습니다."),
    AUTH_INVALID_TOKEN("토큰이 유효하지 않거나 존재하지 않습니다."),
    AUTH_INVALID_REFRESH_TOKEN("리프레시 토큰이 유효하지 않습니다."),
    AUTH_FORBIDDEN("접근할 수 없는 리소스입니다."),
    AUTH_INVALID_CODE("인증코드가 유효하지 않습니다.");

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}