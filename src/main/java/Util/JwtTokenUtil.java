package Util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtTokenUtil {
    public static String createToken(String loginId, SecretKey key, long expireTimeMs){
        Claims claims = Jwts.claims();
        claims.put("loginId", loginId);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expireTimeMs))
                .signWith(key)
                .compact();
    }

    public static String getLonginId(String token, SecretKey secretKey){
        return extractClaims(token,secretKey).get("loginId").toString();
    }
    public static boolean isExpired(String token, SecretKey secretKey){
        Date expiredDate =  extractClaims(token, secretKey).getExpiration();
        return expiredDate.before(new Date());
    }
    public static Claims extractClaims(String token, SecretKey secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
