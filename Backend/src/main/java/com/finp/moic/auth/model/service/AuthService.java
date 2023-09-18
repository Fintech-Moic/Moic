package com.finp.moic.auth.model.service;

import com.finp.moic.auth.model.dto.response.AuthRefreshResponseDTO;

public interface AuthService {

    public AuthRefreshResponseDTO refresh(String refreshToken);

}
