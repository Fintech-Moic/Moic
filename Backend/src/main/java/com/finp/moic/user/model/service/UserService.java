package com.finp.moic.user.model.service;


import com.finp.moic.user.model.dto.request.UserLoginRequestDTO;
import com.finp.moic.user.model.dto.request.UserRegistRequestDTO;
import com.finp.moic.user.model.dto.response.UserLoginResponseDTO;
import com.finp.moic.user.model.dto.response.UserRegistResponseDTO;

public interface UserService {

    UserLoginResponseDTO login(UserLoginRequestDTO dto);

    UserRegistResponseDTO regist(UserRegistRequestDTO dto);

}
