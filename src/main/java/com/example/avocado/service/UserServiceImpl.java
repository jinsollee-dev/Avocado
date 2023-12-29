package com.example.avocado.service;

import com.example.avocado.domain.User;
import com.example.avocado.dto.UserDTO;
import com.example.avocado.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long join(UserDTO userDTO) {
        User user = User.builder()
                .username(userDTO.getUsername())
                .nickname(userDTO.getNickname())
                .name(userDTO.getName())
                .password(userDTO.getPassword())
                .phone(userDTO.getPhone())
                .deleteCheck(true)
                .role("USER")
                .build();

        return userRepository.save(user).getId();
    }

    /* 회원가입 시, 유효성 및 중복 검사 */
    @Transactional(readOnly = true)
    @Override
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }
}
