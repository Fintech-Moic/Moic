package com.finp.moic.auth.model.service;

import com.finp.moic.auth.model.dto.response.AuthRefreshResponseDTO;
import com.finp.moic.util.database.service.RedisService;
import com.finp.moic.util.exception.ExceptionEnum;
import com.finp.moic.util.exception.list.ExpiredTokenException;
import com.finp.moic.util.security.service.JwtProvider;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    public AuthServiceImpl(JwtProvider jwtProvider, RedisService redisService){
        this.jwtProvider = jwtProvider;
        this.redisService = redisService;
    }

    @Override
    public AuthRefreshResponseDTO refresh(String refreshToken) {

        String dbRefreshToken = redisService.getRefreshToken(refreshToken);

        // 만약 Redis에서 토큰이 조회되지 않는다면
        if(dbRefreshToken==null){
            throw new ExpiredTokenException(ExceptionEnum.EXPIRED_TOKEN_ERROR);
        }


        String newAccessToken = jwtProvider.createAccessToken(dbRefreshToken);

        return AuthRefreshResponseDTO.builder()
                .token(newAccessToken)
                .build();
    }
}
