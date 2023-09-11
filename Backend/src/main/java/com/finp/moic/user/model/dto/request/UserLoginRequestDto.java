package com.finp.moic.user.model.dto.request;

import lombok.*;

@Builder
@NoArgsConstructor
@Getter
@AllArgsConstructor
@ToString
public class UserLoginRequestDto {
    private String id;
    private String password;
}
