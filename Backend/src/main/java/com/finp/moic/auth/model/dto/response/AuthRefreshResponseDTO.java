package com.finp.moic.auth.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthRefreshResponseDTO {

    private String token;

    private String refreshToken;

    public AuthRefreshResponseDTO(){

    }

    @Builder
    public AuthRefreshResponseDTO(String token, String refreshToken){
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
