package com.finp.moic.user.model.service;


import com.finp.moic.user.model.dto.request.UserLoginRequestDTO;
import com.finp.moic.user.model.dto.request.UserRegistRequestDTO;
import com.finp.moic.user.model.dto.response.UserLoginResponseDTO;
import com.finp.moic.user.model.dto.response.UserRegistResponseDTO;
import com.finp.moic.user.model.entity.User;
import com.finp.moic.user.model.repository.UserRepository;
import com.finp.moic.util.exception.ExceptionEnum;
import com.finp.moic.util.exception.list.IdOrPasswordNotMatchedException;
import com.finp.moic.util.exception.list.UserNotFoundException;
import com.finp.moic.util.exception.list.ValidationException;
import com.finp.moic.util.security.service.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
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

        String token = jwtProvider.createToken(String.format("%s:%s", user.getId(),user.getName()));

        return UserLoginResponseDTO.builder()
                .name(user.getName())
                .token(token)
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

        /**
        TO DO :: 반환 DTO 확인 필요
        */
        /*** DTO Builder ***/
        return UserRegistResponseDTO.builder()
                .id(registUser.getId())
                .build();
    }
}

