package com.finp.moic.user.controller;


import com.finp.moic.user.model.dto.request.*;
import com.finp.moic.user.model.dto.response.*;
import com.finp.moic.user.model.service.UserService;
import com.finp.moic.util.cookie.CookieService;
import com.finp.moic.util.dto.ResponseDTO;
import com.finp.moic.util.security.dto.UserAuthentication;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CookieService cookieService;

    @Autowired
    public UserController(UserService userService, CookieService cookieService) {
        this.userService = userService;
        this.cookieService = cookieService;
    }

    @PostMapping(value = "/login", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> login(
            @RequestBody @Valid UserLoginRequestDTO dto,
            HttpServletResponse httpResponse
    ){
        UserLoginResponseDTO response = userService.login(dto);

        //쿠키에 refreshToken 담기
        httpResponse.addCookie(cookieService.createCookie(response.getRefreshToken()));

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("로그인 성공")
                .data(response)
                .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO> logout(
            @AuthenticationPrincipal UserAuthentication userAuthentication,
            @CookieValue(name = "refreshToken") String refreshToken,
            HttpServletResponse httpResponse
    ){
        userService.logout(userAuthentication,refreshToken);

        httpResponse.addCookie(cookieService.deleteCookie());
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("로그아웃 성공")
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

    @PostMapping("/check/id")
    public ResponseEntity<ResponseDTO> isIdValidate(
            @RequestBody @Valid UserIdCheckRequestDTO dto
    ){
        UserIdCheckResponseDTO response = userService.isIdValidate(dto);
        String message = "사용 가능한 ID 입니다.";
        if(!response.isValid()){
            message = "중복된 ID 입니다.";
        }
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message(message)
                .data(response)
                .build());
    }

    @PostMapping("/check/email")
    public ResponseEntity<ResponseDTO> isEmailValidate(
            @RequestBody @Valid UserEmailCheckRequestDTO dto
    ){
        UserEmailCheckResponseDTO response = userService.isEmailValidate(dto);

        String message = "사용 가능한 E-mail 입니다.";
        if(!response.isValid()){
            message = "중복된 E-mail 입니다.";
        }
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message(message)
                .data(response)
                .build());
    }

    @PostMapping("/check/password")
    public ResponseEntity<ResponseDTO> isPasswordValidate(
            @RequestBody @Valid UserPasswordCheckRequestDTO dto
            ){
        UserPasswordCheckResponseDTO response = userService.isPasswordValidate(dto);
        String message = "비밀번호 확인 완료";
        if(!response.isValid()){
            message = "비밀번호가 유효하지 않습니다.";
        }
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message(message)
                .data(response)
                .build());
    }

    @PostMapping("/detail")
    public ResponseEntity<ResponseDTO> getUserDetail(
            @AuthenticationPrincipal UserAuthentication userAuthentication
    ){
        UserDetailResponseDTO response = userService.getUserDetail(userAuthentication.getId());

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("회원 정보 조회")
                .data(response)
                .build());
    }

    @PostMapping("/modify")
    public ResponseEntity<ResponseDTO> modifyUser(
            @AuthenticationPrincipal UserAuthentication userAuthentication,
            @RequestBody UserModifyRequestDTO dto
    ){
        UserModifyResponseDTO response = userService.modifyUser(userAuthentication.getId(), dto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("회원 정보 수정")
                .data(response)
                .build());
    }


    @PostMapping("/test")
    public String test(
            @AuthenticationPrincipal UserAuthentication userAuthentication
    ){
        System.out.println("아이디 : " + userAuthentication.getId());
        return "성공";
    }




}