package com.finp.moic.auth.model.service;

import com.finp.moic.auth.model.dto.response.AuthRefreshResponseDTO;
import com.finp.moic.auth.model.entity.RefreshToken;
import com.finp.moic.auth.model.repository.AuthRepository;
import com.finp.moic.util.database.service.RedisService;
import com.finp.moic.util.exception.ExceptionEnum;
import com.finp.moic.util.exception.list.ForgeryTokenException;
import com.finp.moic.util.security.service.JwtProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService{

    private final AuthRepository authRepository;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    public AuthServiceImpl(AuthRepository authRepository, JwtProvider jwtProvider, RedisService redisService){
        this.authRepository = authRepository;
        this.jwtProvider = jwtProvider;
        this.redisService = redisService;
    }

    @Override
    public AuthRefreshResponseDTO refresh(String refreshToken) {

        String dbRefreshToken = redisService.getRefreshToken(refreshToken);
        // 만약 Redis에서 토큰이 조회되지 않는다면
        if(dbRefreshToken==null){
            throw new ForgeryTokenException(ExceptionEnum.FORGERY_TOKEN_ERROR);
        }

        /**
         * 1. 토큰이 없으면 위에서 걸릴거임
         * 2. 또 인증을 해야할까 고민!!
         * 2-1. ID로 인증
         *
         *
         * 2-2 토큰으로 인증...??
         * */


        String newAccessToken = jwtProvider.createToken(dbRefreshToken);
        String newRefreshToken = jwtProvider.createRefreshToken();



        return AuthRefreshResponseDTO.builder()
                .token(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

}
