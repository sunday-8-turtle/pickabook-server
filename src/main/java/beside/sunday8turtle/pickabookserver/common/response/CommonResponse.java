package beside.sunday8turtle.pickabookserver.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
    private Result result;
    private T data;
    private String message;
    private String errorCode;
    private String timestamp;

    public static <T> CommonResponse<T> success(T data, String message) {
        return (CommonResponse<T>) CommonResponse.builder()
                .result(Result.SUCCESS)
                .data(data)
                .message(message)
                .timestamp(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()))
                .build();
    }

    public static <T> CommonResponse<T> success() {
        return success(null, null);
    }

    public static <T> CommonResponse<T> success(T data) {
        return success(data, null);
    }

    public static <T> CommonResponse<T> success(String message) {
        return success(null, message);
    }

    public static <T> CommonResponse fail(T data, String message, String errorCode) {
        return CommonResponse.builder()
                .result(Result.FAIL)
                .data(data)
                .message(message)
                .errorCode(errorCode)
                .timestamp(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()))
                .build();
    }

    public static CommonResponse fail(String message, String errorCode) {
        return CommonResponse.builder()
                .result(Result.FAIL)
                .message(message)
                .errorCode(errorCode)
                .timestamp(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()))
                .build();
    }

    public static CommonResponse fail(ErrorCode errorCode) {
        return CommonResponse.builder()
                .result(Result.FAIL)
                .message(errorCode.getErrorMsg())
                .errorCode(errorCode.name())
                .timestamp(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()))
                .build();
    }

    public enum Result {
        SUCCESS, FAIL
    }
}