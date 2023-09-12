package com.finp.moic.user.controller;


import com.finp.moic.user.model.dto.request.UserLoginRequestDTO;
import com.finp.moic.user.model.dto.request.UserRegistRequestDTO;
import com.finp.moic.user.model.dto.response.UserLoginResponseDTO;
import com.finp.moic.user.model.dto.response.UserRegistResponseDTO;
import com.finp.moic.user.model.service.UserService;
import com.finp.moic.util.dto.ResponseDTO;
import com.finp.moic.util.security.dto.UserAuthentication;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> login(
            @RequestBody @Valid UserLoginRequestDTO dto
    ){
        UserLoginResponseDTO response = userService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("로그인 성공")
                .data(response)
                .build());
    }

    @PostMapping(value = "/regist", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> regist(
            @RequestBody @Valid UserRegistRequestDTO dto
    ){
        UserRegistResponseDTO response = userService.regist(dto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("회원가입 성공")
                .data(response)
                .build());
    }

    @GetMapping("/test")
    public String test(
            @AuthenticationPrincipal UserAuthentication userAuthentication
    ){
        System.out.println("아이디 : " + userAuthentication.getId());
        return "성공";
    }
}