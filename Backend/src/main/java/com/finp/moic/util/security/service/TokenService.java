package com.finp.moic.util.security.service;

import com.finp.moic.util.security.entity.RefreshToken;
import com.finp.moic.util.security.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository){
        this.tokenRepository = tokenRepository;
    }
    public void saveRefreshToken(String refreshToken, String id) {
        RefreshToken token = RefreshToken.builder()
                .userId(id)
                .token(refreshToken)
                .build();

        tokenRepository.save(token);
    }
}
