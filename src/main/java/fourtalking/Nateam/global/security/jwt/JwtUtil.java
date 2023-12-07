package fourtalking.Nateam.global.security.jwt;

import fourtalking.Nateam.global.exception.jwt.ExpiredJwtTokenException;
import fourtalking.Nateam.global.exception.jwt.InvalidJwtSignatureException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtUtil {

    public static final String BEARER_PREFIX = "Bearer ";
    //테스트용 넉넉한 4시간
    private final long TOKEN_TIME = 240 * 60 * 1000L;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(String username, boolean forLogin) {
        Date date = new Date();
        long token_time = forLogin ? TOKEN_TIME : 0;

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .setExpiration(new Date(date.getTime() + token_time))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        
        String tokenWithBearer = request.getHeader("AUTHORIZATION");
        
        if(StringUtils.hasText(tokenWithBearer) && tokenWithBearer.startsWith(BEARER_PREFIX)) {
            
            return tokenWithBearer.substring(7);
        }
        return null;
    }

    public boolean validateToken(String tokenValue) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(tokenValue);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            throw new InvalidJwtSignatureException();
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtTokenException();
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
        }
        return false;
    }

    public Claims getUserInfo(String tokenValue) {

        return Jwts.parserBuilder().setSigningKey(key)
                .build().parseClaimsJws(tokenValue).getBody();
    }
}
