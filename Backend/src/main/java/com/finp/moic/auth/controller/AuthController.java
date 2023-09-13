package com.finp.moic.auth.controller;

import com.finp.moic.auth.model.dto.request.AuthRefreshRequestDTO;
import com.finp.moic.auth.model.dto.response.AuthRefreshResponseDTO;
import com.finp.moic.auth.model.service.AuthService;
import com.finp.moic.util.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseDTO> refresh(
            @RequestBody AuthRefreshRequestDTO dto
    ){
        AuthRefreshResponseDTO response = authService.refresh(dto.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("Refresh 성공")
                .data(response)
                .build());
    }
}
