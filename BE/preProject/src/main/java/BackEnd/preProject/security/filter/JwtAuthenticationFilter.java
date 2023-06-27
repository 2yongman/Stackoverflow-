package BackEnd.preProject.security.filter;

import BackEnd.preProject.accessHistory.LoginHistory;
import BackEnd.preProject.accessHistory.LoginHistoryRepository;
import BackEnd.preProject.member.entity.Member;
import BackEnd.preProject.security.dto.LoginDto;
import BackEnd.preProject.security.jwt.JwtTokenizer;
import BackEnd.preProject.security.refreshtoken.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

//컨트롤러로 가기 전 필터
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;
    private final LoginHistoryRepository loginHistoryRepository;
    private final RedisService redisService;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenizer jwtTokenizer,
                                   LoginHistoryRepository loginHistoryRepository, RedisService redisService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenizer = jwtTokenizer;
        this.loginHistoryRepository = loginHistoryRepository;
        this.redisService = redisService;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(),LoginDto.class);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {
        Member member = (Member) authResult.getPrincipal();
        String accessToken = delegateAccessToken(member);
        String refreshToken = delegateRefreshToken(member);

        response.setHeader("Authorization", "Bearer " + accessToken);
        Cookie refreshTokenCookie = new Cookie("MyRefreshToken",refreshToken);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 30); // 쿠키 유효기간 30일
        response.addCookie(refreshTokenCookie);

        //로그인한 유저의 아이디를 JSON 형식으로 응답
        String username = member.getUsername();
        String email = member.getEmail();
        String nickname = member.getNickname();
        long memberId = member.getMemberId();

        LoginHistory loginHistory = new LoginHistory(username, LocalDateTime.now());
        loginHistoryRepository.save(loginHistory);

        Map<String,String> result = new HashMap<>();

        result.put("\"memberId\"",'"'+String.valueOf(memberId)+'"');
        result.put("\"username\"",'"'+username+'"');
        result.put("\"email\"",'"'+email+'"');
        result.put("\"nickname\"",'"'+nickname+'"');


        response.setContentType("application/json");
        response.getWriter().write(String.valueOf(result).replace("{","{\n").replace(",",",\n")
                .replace("=",":").replace(":"," :"));

        //todo Redis에 refreshtoken 저장
        redisService.saveKeyValueToRedis(member.getUsername(),refreshToken);

    }



    private String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", member.getUsername());
//        claims.put("memberId", member.getMemberId());
        claims.put("roles", member.getRoles());


        String subject = member.getEmail(); /// Todo 토큰에 email 정보를 넣을까 말까? 다른걸 넣을까 고민중
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    private String delegateRefreshToken(Member member) {
        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;

    }



}
