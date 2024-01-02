package com.example.avocado.validator;

import com.example.avocado.dto.user.UserDTO;
import com.example.avocado.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class CheckUserValidator extends AbstractValidator<UserDTO> {

    private final UserRepository userRepository;
    @Override
    protected void doValidate(UserDTO dto, Errors errors) {
        if(userRepository.existsByUsername(dto.getUsername())) {
            errors.rejectValue("username", "UserName 중복 오류", "이미 사용중인 UserName 입니다.");
        }
        if(userRepository.existsByNickname(dto.getNickname())) {
            errors.rejectValue("nickname", "NickName 중복 오류", "이미 사용중인 NickName 입니다.");
        }
    }
}
