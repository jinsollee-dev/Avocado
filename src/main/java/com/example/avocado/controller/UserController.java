package com.example.avocado.controller;

import com.example.avocado.domain.User;
import com.example.avocado.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/user")
public class UserController {
    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public void login(){

    }
    @GetMapping("/join")
    public void join(){
    }

    @PostMapping("/register")
    public String register(User user){
        log.info(user);
        String password=user.getPassword();
        String enPassword=bCryptPasswordEncoder.encode(password);
        user.setPassword(enPassword);
        user.setRole("USER");
        repository.save(user);
        return "redirect:/";
    }
}
