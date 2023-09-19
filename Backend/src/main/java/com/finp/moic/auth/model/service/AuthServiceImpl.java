package com.finp.moic.auth.model.service;

import com.finp.moic.auth.model.dto.response.AuthRefreshResponseDTO;
import com.finp.moic.util.database.service.RedisService;
import com.finp.moic.util.exception.ExceptionEnum;
import com.finp.moic.util.exception.list.ExpiredTokenException;
import com.finp.moic.util.security.service.JwtService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final JwtService jwtService;
    private final RedisService redisService;

    public AuthServiceImpl(JwtService jwtService, RedisService redisService){
        this.jwtService = jwtService;
        this.redisService = redisService;
    }

    @Override
    public AuthRefreshResponseDTO refresh(String accessToken, String refreshToken) {



        /**
         * 여기 로직 작성해야 함
         * 1. 진짜 만료된 access Token인지 확인
         *      => 아니면 refresh 요청을 무한정으로 보낼 수 있기 때문
         * 2. refresh도 만료된건지 확인
         * 3. Redis에서도 확인
         * 4. access에서 회원 아이디 가져와서 Redis와 비교
         *
         * 위 과정이 모두 완료 되면 access 발급
         * */

        String dbRefreshToken = redisService.getRefreshToken(refreshToken);

        // 만약 Redis에서 토큰이 조회되지 않는다면
        if(dbRefreshToken==null){
            throw new ExpiredTokenException(ExceptionEnum.EXPIRED_TOKEN_ERROR);
        }


        String newAccessToken = jwtService.createAccessToken(dbRefreshToken);

        return AuthRefreshResponseDTO.builder()
                .token(newAccessToken)
                .build();
    }
}
