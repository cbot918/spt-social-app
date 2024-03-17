package com.yale.fullstackbackend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Map;
import java.util.UUID;

import java.util.Date;
import io.jsonwebtoken.*;

import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.expire_time}")
    private int expireTime;

    @Value("${jwt.secret}")
    private String secret;
    /**
     * 初始化 Token 方法
     *
     * @param Map<String, Object> claims JWT要附加的屬性
     * @param String      subject 識別主體值
     *
     * @return String
     */
    public String generateToken(
            Map<String, Object> payload,
            String subject) {

        Date expireDate = new Date(System.currentTimeMillis() + expireTime);
        // 產生隨機 UUDI 當作JWT ID
        String jwtIdentityId = UUID.randomUUID().toString();

        JwtBuilder jwtBuilder = Jwts.builder();
        if (payload != null) {
            Claims useClaims = Jwts.claims(payload);
            jwtBuilder.setClaims(useClaims);
        }

        return jwtBuilder.setSubject(subject)
                .setId(jwtIdentityId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expireDate)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 將 String 轉換成 Key物件
     *
     * @return io.jsonwebtoken.security.Keys
     */
    private SecretKey generateKey() {
        byte[] encodeKey = secret.getBytes();
        // Secret 轉換成符合 SHA規範的值
        return Keys.hmacShaKeyFor(encodeKey);
    }

    /**
     *
     * @param String token 要解析的 Token
     *
     * @return Claims
     */
    public Claims parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey(generateKey())
                .build().parseClaimsJws(token).getBody();
    }

    /**
     * @return <T> T 實際回傳型別依套用的 resovler為主
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 驗證 Tokem是否逾期
     *
     * @return Boolean
     */
    public Boolean validateToken(String token) {
        final Date expiration = getClaimFromToken(token, Claims::getExpiration);
        return expiration.before(new Date()) == false;
    }

}
