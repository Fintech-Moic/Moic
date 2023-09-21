package com.finp.moic.user.model.service;


import com.finp.moic.user.model.dto.request.*;
import com.finp.moic.user.model.dto.response.*;

public interface UserService {

    UserLoginResponseDTO login(UserLoginRequestDTO dto);
    UserRegistResponseDTO regist(UserRegistRequestDTO dto);
    UserIdCheckResponseDTO isIdValidate(UserIdCheckRequestDTO dto);
    UserEmailCheckResponseDTO isEmailValidate(UserEmailCheckRequestDTO dto);
    UserPasswordCheckResponseDTO isPasswordValidate(UserPasswordCheckRequestDTO dto);


}
