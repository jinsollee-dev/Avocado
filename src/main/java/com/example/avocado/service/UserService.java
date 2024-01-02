package com.example.avocado.service;

import com.example.avocado.dto.user.UserDTO;
import com.example.avocado.dto.user.UserResponseDTO;
import org.springframework.validation.Errors;

import java.util.Map;

public interface UserService {

    Long join(UserDTO userDTO);
    Map<String, String> validateHandling(Errors errors);

    UserResponseDTO findUser(String username);
}
