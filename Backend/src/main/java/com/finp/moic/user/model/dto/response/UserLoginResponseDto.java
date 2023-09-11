package com.finp.moic.user.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class UserLoginResponseDto {
    private String name;
    private String token;
}
