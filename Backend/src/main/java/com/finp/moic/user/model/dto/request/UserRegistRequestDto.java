package com.finp.moic.user.model.dto.request;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRegistRequestDto {
    private String id;
    private String password;
    private String name;
}
