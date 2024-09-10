package com.cps.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

public class JWTUtils {

    private SecretKey secretKey;
    private long accessValidityInMilliseconds = 3600000; // 1시간
    private long refreshValidityInMilliseconds = 86400000; // 24시간

    public JWTUtils(String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String createToken(String username, String clientId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessValidityInMilliseconds);

        return Jwts.builder()
                .subject(username)
                .issuer("cps")
                .claim("clientId", clientId)
                .expiration(validity)
                .signWith(secretKey,  Jwts.SIG.HS512)
                .compact();
    }

    public String createRefreshToken(String username, String clientId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshValidityInMilliseconds);

        return Jwts.builder()
                .subject(username)
                .issuer("cps")
                .claim("clientId", clientId)
                .expiration(validity)
                .signWith(secretKey,  Jwts.SIG.HS512)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser().verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return parseToken(token).getSubject();
    }
}
