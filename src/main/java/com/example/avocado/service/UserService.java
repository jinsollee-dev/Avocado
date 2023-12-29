package com.example.avocado.service;

import com.example.avocado.dto.UserDTO;
import org.springframework.validation.Errors;

import java.util.Map;

public interface UserService {

    Long join(UserDTO userDTO);
    Map<String, String> validateHandling(Errors errors);
}
