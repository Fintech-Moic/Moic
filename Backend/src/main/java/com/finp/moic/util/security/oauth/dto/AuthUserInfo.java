package com.finp.moic.util.security.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AuthUserInfo {

    private String id;
    private String email;
    private List<String> roles;

    @Override
    public String toString() {
        return "[AuthUserInfo = id: " + id + " email: " + email + " role: " + roles + "]";
    }
}