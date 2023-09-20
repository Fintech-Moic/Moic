package com.finp.moic.user.model.dto.response;

import lombok.*;

@Getter
@ToString
public class UserLoginResponseDTO {

    private String name;

    private String accessToken;

    private String refreshToken;

    public UserLoginResponseDTO() {
    }

    @Builder
    public UserLoginResponseDTO(String name, String accessToken, String refreshToken) {
        this.name = name;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
