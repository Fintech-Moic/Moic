package com.finp.moic.user.model.service;


import com.finp.moic.user.model.dto.request.UserEmailCheckRequestDTO;
import com.finp.moic.user.model.dto.request.UserIdCheckRequestDTO;
import com.finp.moic.user.model.dto.request.UserLoginRequestDTO;
import com.finp.moic.user.model.dto.request.UserRegistRequestDTO;
import com.finp.moic.user.model.dto.response.UserEmailCheckResponseDTO;
import com.finp.moic.user.model.dto.response.UserIdCheckResponseDTO;
import com.finp.moic.user.model.dto.response.UserLoginResponseDTO;
import com.finp.moic.user.model.dto.response.UserRegistResponseDTO;
import com.finp.moic.user.model.entity.User;
import com.finp.moic.user.model.repository.UserRepository;
import com.finp.moic.util.database.service.RedisService;
import com.finp.moic.util.exception.ExceptionEnum;
import com.finp.moic.util.exception.list.IdOrPasswordNotMatchedException;
import com.finp.moic.util.exception.list.UserNotFoundException;
import com.finp.moic.util.exception.list.ValidationException;
import com.finp.moic.util.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RedisService redisService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService
                            ,RedisService redisService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.redisService = redisService;
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
    public UserRegistResponseDTO regist(UserRegistRequestDTO dto) {

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
        Optional<User> byEmail = userRepository.findByEmail(dto.getEmail());
        if(byEmail.isPresent()){
            return UserEmailCheckResponseDTO.builder()
                    .isValid(false)
                    .build();
        }
        return UserEmailCheckResponseDTO.builder()
                .isValid(true)
                .build();
    }

}

