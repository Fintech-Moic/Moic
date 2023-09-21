package com.finp.moic.user.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserEmailCheckResponseDTO {
    private boolean isValid;

    public UserEmailCheckResponseDTO(){

    }

    @Builder
    public UserEmailCheckResponseDTO(boolean isValid){
        this.isValid = isValid;
    }
}
