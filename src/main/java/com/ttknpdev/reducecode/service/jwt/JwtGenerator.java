package com.ttknpdev.reducecode.service.jwt;

import com.ttknpdev.reducecode.exception.TokenExpiredNotAllowed;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service // ** mark service for using @Value annotation
public class JwtGenerator {
    // Basic
    private final long ONE_MINUTE = 60000; // 1 min = 60000 ms
    // private final long MINUTES = 100 * 1000; // when i * 1000 it means 100,000 milliseconds or 100 seconds or 1.40 s
    private final long MILLISECONDS = ONE_MINUTE * 5;
    private Logger logger;
    @Value("${JWT.SECRET}")
    private String secret;

    public JwtGenerator() {
        logger = LoggerFactory.getLogger(JwtGenerator.class);
    }

    // *** Main point for build token
    public Map<String, String> generateToken(String username) {
        Map<String, String> jwtTokenGen = new HashMap<>();
        String jwtToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                // new Date(System.currentTimeMillis()) returns 2567-07-08 22:49:16 as real time
                // i have to plus with milliseconds
                .setExpiration(new Date(System.currentTimeMillis() + MILLISECONDS))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        // now token dose exist
        jwtTokenGen.put("token", jwtToken);
        return jwtTokenGen;
    }


    /**
    // *** for validate token
    private Boolean validateToken(String token,String usernameLoggedIn) {
        final String username = getUsernameFromToken(token); // ***** 3 methods for get username
        // username (that find by token) equals username
        if (username.equals(usernameLoggedIn) && !isTokenExpired(token)) {
            return true;
        } else {
            return false;
        }
    }
    */


    // **** For get username 1.
    private String getUsernameFromToken(String token) {
        return getClaimFromToken(token , Claims::getSubject); // getSubject may return username
    }
    // retrieve username from jwt token 2.
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        // claims.getSubject(); // subject as username
        return claimsResolver
                .apply(claims);
    }
    // *** retrieve any information from token we will need the secret key ** Claim (n เรียกร้อง) 3.
    // i throw this exception i will catch it on JwtFilter class
    private Claims getAllClaimsFromToken(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
    // **** For get username


    /**
    // check if the token has expired (v. หมดอายุแล้ว)
    private Boolean isTokenExpired(String token) {
        // retrieve expiration from jwt token
        final Date expiration = getClaimFromToken(token,Claims::getExpiration);
        return expiration
                .before(new Date());
    }
    */

}
