package beside.sunday8turtle.pickabookserver.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate redisTemplate;

    // 키-벨류 설정
    public void setValues(String token, String email, long duration){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(token, email, Duration.ofMillis(duration));
    }

    // 키값으로 벨류 가져오기
    public String getValues(String token){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(token);
    }

    // 키-벨류 삭제
    public void delValues(String token) {
        redisTemplate.delete(token.substring(7));
    }
}
