package com.finp.moic.user.model.service;


import com.finp.moic.user.model.dto.request.*;
import com.finp.moic.user.model.dto.response.*;
import com.finp.moic.util.security.dto.UserAuthentication;
import com.finp.moic.util.security.oauth.dto.AuthUserInfo;
import com.finp.moic.util.security.oauth.dto.OAuthUserInfo;

public interface UserService {

    UserLoginResponseDTO login(UserLoginRequestDTO dto);
    UserRegistResponseDTO regist(UserRegistRequestDTO dto);
    UserIdCheckResponseDTO isIdValidate(UserIdCheckRequestDTO dto);
    UserEmailCheckResponseDTO isEmailValidate(UserEmailCheckRequestDTO dto);
    void isPasswordValidate(String id, UserPasswordCheckRequestDTO dto);
    void logout(UserAuthentication userAuthentication,String refreshToken);
    UserDetailResponseDTO getUserDetail(String id);
    void modifyUser(String id, UserModifyRequestDTO dto);
    void modifyPassword(String id, UserModifyPasswordRequestDTO dto);
    void deleteUser(String id);

    AuthUserInfo getOrRegisterUser(OAuthUserInfo oAuthUserInfo);
}
