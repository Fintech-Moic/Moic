package com.finp.moic.user.model.service;


import com.finp.moic.user.model.dto.request.UserLoginRequestDto;
import com.finp.moic.user.model.dto.request.UserRegistRequestDto;
import com.finp.moic.user.model.dto.response.UserLoginResponseDto;
import com.finp.moic.user.model.dto.response.UserRegistResponseDto;

public interface UserService {

    UserLoginResponseDto login(UserLoginRequestDto dto);

    UserRegistResponseDto regist(UserRegistRequestDto dto);

}
