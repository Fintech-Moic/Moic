package com.finp.moic.user.model.dto.response;

import lombok.*;

@Getter
@ToString
public class UserLoginResponseDTO {

    private String name;

    private String token;

    public UserLoginResponseDTO() {
    }

    @Builder
    public UserLoginResponseDTO(String name, String token) {
        this.name = name;
        this.token = token;
    }
}
