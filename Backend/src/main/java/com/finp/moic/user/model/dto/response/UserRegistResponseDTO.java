package com.finp.moic.user.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class UserRegistResponseDTO {
    private String id;

    public UserRegistResponseDTO() {
    }

    @Builder
    public UserRegistResponseDTO(String id) {
        this.id = id;
    }
}
