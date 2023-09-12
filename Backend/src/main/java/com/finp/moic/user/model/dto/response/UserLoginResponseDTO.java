package com.finp.moic.user.model.dto.response;

import lombok.*;

@Getter
@ToString
public class UserLoginResponseDTO {

    private String name;

    private String token;

    private String refreshToken;

    public UserLoginResponseDTO() {
    }

    @Builder
    public UserLoginResponseDTO(String name, String token, String refreshToken) {
        this.name = name;
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
