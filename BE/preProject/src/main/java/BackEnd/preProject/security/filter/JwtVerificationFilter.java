package BackEnd.preProject.security.filter;

import BackEnd.preProject.security.jwt.JwtTokenizer;
import BackEnd.preProject.security.refreshtoken.RedisService;
import BackEnd.preProject.security.utils.CustomAuthorityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final RedisService redisService;

    public JwtVerificationFilter(JwtTokenizer jwtTokenizer,
                                 CustomAuthorityUtils authorityUtils,
                                 RedisService redisService) {
        this.authorityUtils = authorityUtils;
        this.jwtTokenizer = jwtTokenizer;
        this.redisService = redisService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Map<String, Object> claims = verifyJws(request);
        setAuthenticationToContext(claims);

        filterChain.doFilter(request,response);
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");
        return authorization ==null || !authorization.startsWith("Bearer");
    }

    //todo JWT 검증
    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer", "");
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Cookie[] cookies = request.getCookies();
        String myRefreshToken = "";
        for (Cookie cookie : cookies){
            if ("MyRefreshToken".equals(cookie.getName())){
                myRefreshToken = cookie.getValue();
                break;
            }
        }
        Claims decodedRefreshToken = jwtTokenizer.decodeRefreshToken(myRefreshToken, base64EncodedSecretKey);

        try {
            // Access Token 검증
            jwtTokenizer.verifySignature(jws, base64EncodedSecretKey);
            Jws<Claims> accessTokenClaims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey);

            // Access Token이 만료되었는지 확인, 만료되었으면 새로운 토큰 발행 후 클레임 리턴
            Date expirationTime = accessTokenClaims.getBody().getExpiration();
            Date currentTime = new Date();
            if (expirationTime != null && currentTime.before(expirationTime)) {
                // Access Token이 만료된 경우 Refresh Token을 검증하여 재발급 로직 수행
                if (myRefreshToken != null) {
                    // Redis 등을 통해 Refresh Token의 유효성 검증
                    boolean isRefreshTokenValid = redisService.verifyRefreshToken(decodedRefreshToken.getSubject(), myRefreshToken);
                    if (isRefreshTokenValid) {
                        // Refresh Token이 유효한 경우 새로운 Access Token 발급
                        Date newAccessTokenExpiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
                        String newAccessToken = jwtTokenizer.generateAccessToken(accessTokenClaims.getBody(), decodedRefreshToken.getSubject(), newAccessTokenExpiration, base64EncodedSecretKey);

                        // 반환할 데이터 설정
                        Map<String, Object> claims = accessTokenClaims.getBody();
                        claims.put("accessToken", newAccessToken);

                        return claims;
                    } else {
                        // Refresh Token이 유효하지 않은 경우
                        throw new IllegalArgumentException("Refresh Token is invalid");
                    }
                }
            }
            // Access Token이 유효하고 만료되지 않은 경우, 기존의 클레임 정보 반환
            else {
                return accessTokenClaims.getBody();
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Token verification failed.", e);
        }
        return null;
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        String username = (String) claims.get("username");
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List)claims.get("roles") );
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
