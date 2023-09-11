package com.finp.moic.util.security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtProvider {
    private final String secretKey;
    private final long expirationHours;
    private final String issuer;

    public JwtProvider(
            @Value("${issuer}") String issuer,
            @Value("${expiration-hours}") long expirationHours,
            @Value("${secret-key}") String secretKey
    ) {
        this.issuer=issuer;
        this.expirationHours=expirationHours;
        this.secretKey=secretKey;
    }

    public String createToken(String userSpecification){
        return Jwts.builder()
                .signWith((new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))) //HS512 알고리즘 이용, secretKey 이용
                .setSubject(userSpecification) //토큰 이름
                .setIssuer(issuer) //발급자
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now())) //발급시간
                .setExpiration(Date.from(Instant.now().plus(expirationHours, ChronoUnit.HOURS))) //만료시간
                .compact();
    }

    public String getSubject(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
