package com.finp.moic.user.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserIdCheckResponseDTO {
    private boolean isValid;

    public UserIdCheckResponseDTO(){

    }

    @Builder
    public UserIdCheckResponseDTO(boolean isValid){
        this.isValid = isValid;
    }
}
