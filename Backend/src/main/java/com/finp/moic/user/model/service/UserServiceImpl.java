package com.finp.moic.user.model.service;


import com.finp.moic.user.model.dto.request.*;
import com.finp.moic.user.model.dto.response.*;
import com.finp.moic.user.model.entity.User;
import com.finp.moic.user.model.repository.UserRepository;
import com.finp.moic.util.database.service.RedisService;
import com.finp.moic.util.exception.ExceptionEnum;
import com.finp.moic.util.exception.list.*;
import com.finp.moic.util.security.dto.UserAuthentication;
import com.finp.moic.util.security.oauth.dto.AuthUserInfo;
import com.finp.moic.util.security.oauth.dto.OAuthUserInfo;
import com.finp.moic.util.security.oauth.util.HashUtil;
import com.finp.moic.util.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RedisService redisService;
    private final HashUtil hashUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService
                            ,RedisService redisService, HashUtil hashUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.redisService = redisService;
        this.hashUtil = hashUtil;
    }

    @Override
    public UserLoginResponseDTO login(UserLoginRequestDTO dto){
        // 만약 아이디가 조회되지 않으면
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new UserNotFoundException(ExceptionEnum.USER_NOT_FOUND));

        //아이디는 조회 됐는데 비밀번호가 틀리면
        if(!user.getId().equals(dto.getId()) || !passwordEncoder.matches(dto.getPassword(),user.getPassword())){
            throw new IdOrPasswordNotMatchedException(ExceptionEnum.USER_INVALID);
        }

        //로그인 하고 토큰에 id 저장
        String accessToken = jwtService.createAccessToken(user.getId());
        String refreshToken = jwtService.createRefreshToken();

        //Redis에 저장
        redisService.setRefreshToken(refreshToken, user.getId());

        return UserLoginResponseDTO.builder()
                .name(user.getName())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void logout(UserAuthentication userAuthentication, String refreshToken){

        // 1. 인증 객체 지우기
        SecurityContextHolder.clearContext();

        // 2. Redis에서 refresh 지우기
        redisService.deleteRefreshToken(refreshToken);
    }

    @Override
    public UserRegistResponseDTO regist(UserRegistRequestDTO dto) {

        Optional<User> byId = userRepository.findById(dto.getId());
        if(byId.isPresent()){
            throw new AlreadyExistException(ExceptionEnum.USER_REGIST_DUPLICATE);
        }

        /*** Validation ***/
        if(!dto.getPassword().equals(dto.getPasswordCheck())){
            throw new ValidationException(ExceptionEnum.USER_REGIST_ERROR);
        }

        /*** RDB Access ***/
        User user = User.builder()
                .id(dto.getId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .yearOfBirth(dto.getYearOfBirth())
                .build();

        User registUser = userRepository.save(user);

        /*** DTO Builder ***/
        return UserRegistResponseDTO.builder()
                .id(registUser.getId())
                .build();
    }

    @Override
    public UserIdCheckResponseDTO isIdValidate(UserIdCheckRequestDTO dto){
        Optional<User> byId = userRepository.findById(dto.getId());
        if(byId.isPresent()){
            return UserIdCheckResponseDTO.builder()
                    .isValid(false)
                    .build();
        }
        return UserIdCheckResponseDTO.builder()
                .isValid(true)
                .build();
    }

    @Override
    public UserEmailCheckResponseDTO isEmailValidate(UserEmailCheckRequestDTO dto){
        User user = userRepository.findByEmail(dto.getEmail());
        if(user!=null){
            return UserEmailCheckResponseDTO.builder()
                    .isValid(false)
                    .build();
        }
        return UserEmailCheckResponseDTO.builder()
                .isValid(true)
                .build();
    }

    @Override
    public void isPasswordValidate(String id, UserPasswordCheckRequestDTO dto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ExceptionEnum.USER_NOT_FOUND));
        if(!passwordEncoder.matches(dto.getPassword(),user.getPassword())){
            throw new PasswordNotMatchedException(ExceptionEnum.USER_INVALID_PASSWORD);
        }

    }

    @Override
    public UserDetailResponseDTO getUserDetail(String id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ExceptionEnum.USER_NOT_FOUND));

        return UserDetailResponseDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .gender(user.getGender())
                .yearOfBirth(user.getYearOfBirth())
                .build();
    }

    @Override
    @Transactional
    public void modifyUser(String id, UserModifyRequestDTO dto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ExceptionEnum.USER_NOT_FOUND));

        user.setGender(dto.getGender());
        user.setYearOfBirth(dto.getYearOfBirth());
    }

    @Override
    @Transactional
    public void modifyPassword(String id, UserModifyPasswordRequestDTO dto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ExceptionEnum.USER_NOT_FOUND));

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
    }

    @Override
    @Transactional
    public void deleteUser(String id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ExceptionEnum.USER_NOT_FOUND));

        userRepository.delete(user);
    }

    @Override
    @Transactional
    public AuthUserInfo getOrRegisterUser(OAuthUserInfo oauthUserInfo) {

        // 유저가 존재하는지 확인
        User user = userRepository.findByEmail(oauthUserInfo.getEmail());

        if(user==null){
            user = User.builder()
                    .id(hashUtil.makeHashId())
                    .password(passwordEncoder.encode(hashUtil.makeHashPassword()))
                    .name(oauthUserInfo.getNickname())
                    .email(oauthUserInfo.getEmail())
                    .gender(null)
                    .yearOfBirth(0)
                    .build();


            userRepository.save(user);
        }
        return new AuthUserInfo(user.getId(), user.getEmail(), Arrays.asList("USER"));

    }
}

