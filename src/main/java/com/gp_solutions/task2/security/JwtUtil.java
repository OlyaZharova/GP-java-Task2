package com.gp_solutions.task2.security;

import com.gp_solutions.task2.model.JwtRequest;
import io.jsonwebtoken.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {
    private final String jwtAccessSecret;

    public JwtUtil(
            @Value("${jwt.secret.access}") String jwtAccessSecret
    ) {
        this.jwtAccessSecret = jwtAccessSecret;
    }

    public String generateAccessToken(@NonNull JwtRequest jwtRequest) {
        final String accessToken = Jwts.builder()
                .setIssuer(jwtRequest.getIssuer())
                .setSubject(jwtRequest.getSubject())
                .setExpiration(jwtRequest.getExpirationDate())
                .signWith(SignatureAlgorithm.HS512, jwtAccessSecret)
                .claim("roles", jwtRequest.getRoles())
                .compact();
        return accessToken;
    }

    public boolean validateAccessToken(@NonNull String token) {
        return validateToken(token, jwtAccessSecret);
    }


    private boolean validateToken(@NonNull String token, @NonNull String secret) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            if (!claims.getIssuer().equals("GP")) {
                return false;
            }
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    private Claims getClaims(@NonNull String token, @NonNull String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

}
