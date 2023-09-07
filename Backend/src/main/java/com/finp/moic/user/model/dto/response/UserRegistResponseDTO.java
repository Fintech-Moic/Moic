package com.finp.moic.user.model.dto.response;

import lombok.*;

@Getter
@Builder
@ToString
public class UserRegistResponseDTO {

    private String id;

    public UserRegistResponseDTO() {

    }
    @Builder
    public UserRegistResponseDTO(String id) {
        this.id = id;
    }
}
