package com.finp.moic.user.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserModifyPasswordRequestDTO {


    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,16}$")
    private String password;

    public UserModifyPasswordRequestDTO() {
    }

    @Builder
    public UserModifyPasswordRequestDTO(@NotNull String password) {
        this.password = password;
    }
}
