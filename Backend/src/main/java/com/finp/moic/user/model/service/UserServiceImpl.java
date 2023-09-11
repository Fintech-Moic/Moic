package com.finp.moic.user.model.service;


import com.finp.moic.user.model.dto.request.UserLoginRequestDto;
import com.finp.moic.user.model.dto.request.UserRegistRequestDto;
import com.finp.moic.user.model.dto.response.UserLoginResponseDto;
import com.finp.moic.user.model.dto.response.UserRegistResponseDto;
import com.finp.moic.user.model.entity.User;
import com.finp.moic.user.model.repository.UserRepository;
import com.finp.moic.util.exception.ExceptionEnum;
import com.finp.moic.util.exception.IdOrPasswordNotMatchedException;
import com.finp.moic.util.exception.UserNotFoundException;
import com.finp.moic.util.security.service.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public UserLoginResponseDto login(UserLoginRequestDto dto){
        // 만약 아이디가 조회되지 않으면
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new UserNotFoundException(ExceptionEnum.USER_NOT_FOUND));

        //아이디는 조회 됐는데 비밀번호가 틀리면
        if(!user.getId().equals(dto.getId()) || !passwordEncoder.matches(dto.getPassword(),user.getPw())){
            throw new IdOrPasswordNotMatchedException(ExceptionEnum.USER_INVALID);
        }

        String token = jwtProvider.createToken(String.format("%s:%s", user.getId(),user.getName()));

        return UserLoginResponseDto.builder()
                .name(user.getName())
                .token(token)
                .build();
    }

    @Override
    public UserRegistResponseDto regist(UserRegistRequestDto dto) {

        User user = User.builder()
                .id(dto.getId())
                .pw(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .build();

        User registUser = userRepository.save(user);

        return UserRegistResponseDto.builder()
                .id(registUser.getId())
                .build();
    }
}

