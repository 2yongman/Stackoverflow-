package BackEnd.preProject.security.refreshtoken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Id;
import java.util.Collection;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(timeToLive = 604800)
public class RefreshToken {

    private String id;

    @Indexed
    private String refreshToken;

}
