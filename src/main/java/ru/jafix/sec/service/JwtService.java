package ru.jafix.sec.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.jafix.sec.entity.User;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
public class JwtService {
    private static final String ROLE_CLAIM = "role";
    private static final long EXPIRATION_MS = 300000;

    @Value("${jwt.secret}")
    private String secret;

    public String generate(User user) {
        Date currentDate = new Date();

        return Jwts.builder()
                .signWith(getSecretKey(), Jwts.SIG.HS512)
                .subject(user.getEmail())
                .issuedAt(currentDate)
                .expiration(new Date(currentDate.getTime() + EXPIRATION_MS))
                .claim(ROLE_CLAIM, user.getAuthorities().stream().findFirst().get().getAuthority())
                .compact();
    }

    public boolean validate(String token) {
        try {
            parse(token);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return false;
    }

    public Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSecretKey() {
        byte[] encodeKey = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(encodeKey);
    }
}
