package com.finp.moic.user.model.service;


import com.finp.moic.user.model.dto.request.*;
import com.finp.moic.user.model.dto.response.*;
import com.finp.moic.util.security.dto.UserAuthentication;

public interface UserService {

    UserLoginResponseDTO login(UserLoginRequestDTO dto);
    UserRegistResponseDTO regist(UserRegistRequestDTO dto);
    UserIdCheckResponseDTO isIdValidate(UserIdCheckRequestDTO dto);
    UserEmailCheckResponseDTO isEmailValidate(UserEmailCheckRequestDTO dto);
    UserPasswordCheckResponseDTO isPasswordValidate(UserPasswordCheckRequestDTO dto);
    void logout(UserAuthentication userAuthentication,String refreshToken);
    UserDetailResponseDTO getUserDetail(String id);
    UserModifyResponseDTO modifyUser(String id, UserModifyRequestDTO dto);


}
