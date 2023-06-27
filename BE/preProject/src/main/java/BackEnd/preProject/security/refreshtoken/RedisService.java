package BackEnd.preProject.security.refreshtoken;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
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

    public boolean verifyRefreshToken(String username,String refreshToken){
        String storedRefreshToken = this.getValues(username);
        if (storedRefreshToken != null && storedRefreshToken.equals(refreshToken)){
            return true;
        }else {
            throw new RuntimeException("Refresh Token is invalid");
        }
    }
}
