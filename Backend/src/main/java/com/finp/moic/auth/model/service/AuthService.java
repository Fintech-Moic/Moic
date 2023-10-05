package com.finp.moic.auth.model.service;

import com.finp.moic.auth.model.dto.response.AuthRefreshResponseDTO;
import jakarta.servlet.http.HttpServletResponse;

import java.net.http.HttpResponse;

public interface AuthService {

    public AuthRefreshResponseDTO refresh(String accessToken, String refreshToken, HttpServletResponse httpResponse);

}
