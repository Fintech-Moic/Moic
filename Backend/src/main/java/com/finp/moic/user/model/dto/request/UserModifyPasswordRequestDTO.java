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

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,16}$")
    private String passwordCheck;

    public UserModifyPasswordRequestDTO() {
    }

    @Builder
    public UserModifyPasswordRequestDTO(@NotNull String password, @NotNull String passwordCheck) {
        this.password = password;
        this.passwordCheck = passwordCheck;
    }
}
