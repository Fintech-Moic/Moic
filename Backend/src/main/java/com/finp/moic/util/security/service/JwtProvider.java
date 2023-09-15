package com.finp.moic.util.security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
public class JwtProvider {
    private static final long MILLI_SECOND = 1000L;
    private final String secretKey;

    //30분
    private final long expirationHours = 60 * 30;
    private final String issuer;

    //30일
    private final long refreshTokenExpire = 60 * 60 * 24 * 30;

    public JwtProvider(
            @Value("${issuer}") String issuer,
            @Value("${secret-key}") String secretKey
    ) {
        this.issuer=issuer;
        this.secretKey=secretKey;
    }

    public String createToken(String userSpecification){
        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationHours * MILLI_SECOND);

        return Jwts.builder()
                .signWith((new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))) //HS512 알고리즘 이용, secretKey 이용
                .setSubject(userSpecification) //토큰 이름
                .setIssuer(issuer) //발급자
                .setIssuedAt(now) //발급시간
                .setExpiration(validity) //만료시간
                .compact();
    }

    public String createRefreshToken(){
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenExpire * MILLI_SECOND);

        return Jwts.builder()
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(validity)
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
