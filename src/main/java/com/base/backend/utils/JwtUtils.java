package com.base.backend.utils;


import com.base.backend.common.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.joda.time.DateTime;

import java.security.Key;
import java.util.UUID;

/**
 * @author kamen
 */
public class JwtUtils {

    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static String getToken(String subject) {
        Claims claims = Jwts.claims().setId(UUID.randomUUID().toString()).setSubject(subject);
        DateTime currentTime = new DateTime();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentTime.toDate())
                .setExpiration(currentTime.plusMinutes(Constants.TOKEN_EXPIRATION_TIME).toDate())
                .signWith(KEY)
                .compact();
    }

    public static Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
    }
}
