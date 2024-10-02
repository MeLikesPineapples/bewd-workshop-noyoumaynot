package han.aim.se.noyoumaynot.movie.security;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.sql.Date;
import java.time.LocalDate;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-days}")
    private long jwtExpirationDate;

    public String generateToken(Authentication authentication) {

        String username = authentication.getName();

        LocalDate issueDate = LocalDate.now();
        LocalDate expireDate = issueDate.plusDays(jwtExpirationDate);

        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.valueOf(issueDate))
                .expiration(Date.valueOf(expireDate))
                .signWith(key())
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsername(String token) {

        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        Jwt<?, ?> jwt = Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parse(token);
        return jwt != null;
    }
}
