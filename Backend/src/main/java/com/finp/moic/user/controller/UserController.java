package com.finp.moic.user.controller;


import com.finp.moic.user.model.dto.request.UserLoginRequestDto;
import com.finp.moic.user.model.dto.request.UserRegistRequestDto;
import com.finp.moic.user.model.dto.response.UserLoginResponseDto;
import com.finp.moic.user.model.dto.response.UserRegistResponseDto;
import com.finp.moic.user.model.entity.User;
import com.finp.moic.user.model.service.UserService;
import com.finp.moic.util.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public String home(){
        return "hi";
    }

    @PostMapping(value = "/login", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> login(
            @RequestBody UserLoginRequestDto dto
            ){
        System.out.println("로그인 할겡");
        System.out.println(dto.toString());
        UserLoginResponseDto response = userService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("로그인 성공")
                .data(response)
                .build());
    }

    @PostMapping(value = "/regist", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> regist(
            @RequestBody UserRegistRequestDto dto
    ){
        System.out.println("회원가입 할겡");
        System.out.println(dto.toString());
        UserRegistResponseDto response = userService.regist(dto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("회원가입 성공")
                .data(response)
                .build());
    }

    @GetMapping("/test")
    public String test(
            @AuthenticationPrincipal User user
    ){
        System.out.println("아이디 : " + user.getId());
        System.out.println("이름 : " + user.getName());
        System.out.println("비번 : " + user.getPw());
        System.out.println("시퀀스 : " + user.getSeq());
        return "성공";
    }
}

