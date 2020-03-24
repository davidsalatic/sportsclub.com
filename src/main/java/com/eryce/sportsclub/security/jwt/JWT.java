package com.eryce.sportsclub.security.jwt;

import com.eryce.sportsclub.models.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWT {

    public static String SECRET_KEY = "95686e11-94e5-4702-8b3b-126014658475";

    public static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public static String generateToken(AppUser appUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",appUser.getRole().getName());
        return createToken(claims, appUser.getUsername());
    }

    public static String createToken(Map<String,Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, JWT.SECRET_KEY).compact();
    }

}
