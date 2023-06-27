package BackEnd.preProject.security.refreshtoken;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private final RefreshTokenRepository refreshTokenRepository;

    public RedisService(RedisTemplate<String, String> redisTemplate, RefreshTokenRepository refreshTokenRepository) {
        this.redisTemplate = redisTemplate;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public void saveKeyValueToRedis(String key, String value){
        redisTemplate.opsForValue().set(key,value);
    }

    public void setValues(String key, String data, Duration duration){
        ValueOperations<String,String> values = redisTemplate.opsForValue();
        values.set(key,data,duration);
    }

    public String getValues(String key){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }
    public void deleteValues(String key){
        redisTemplate.delete(key);
    }

}
