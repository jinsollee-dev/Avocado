package com.example.avocado.controller;

import com.example.avocado.dto.user.UserDTO;
import com.example.avocado.dto.user.UserResponseDTO;
import com.example.avocado.repository.UserRepository;
import com.example.avocado.service.UserService;
import com.example.avocado.validator.CheckUserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/user")
public class UserController {
    private final UserRepository repository;
    private final UserService userService;
    private final CheckUserValidator checkUserValidator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /*유효성 검증*/
    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkUserValidator);
    }

    @GetMapping("/login")
    public void login() {

    }

    @GetMapping("/join")
    public void join() {
    }

    /**
     * 회원 가입 post
     *
     * @param userDTO 회원 정보
     * @return 홈페이지
     */
    @PostMapping("/register")
    public String register(@Valid UserDTO userDTO, Errors errors, Model model) {
        log.info(userDTO);
        /*검증*/
        if (errors.hasErrors()) {
            /* 회원가입 실패 시 입력 데이터 유지 */
            model.addAttribute("dto", userDTO);

            /* 유효성 검사를 통과하지 못한 필드와 메세지 핸들링 */
            Map<String, String> validatorResult = userService.validateHandling(errors);

            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
                log.info(key + "/" + validatorResult.get(key));
            }
            /*회원가입 페이지로 리턴*/
            return "/user/join";
        }

        String password = userDTO.getPassword();
        String enPassword = bCryptPasswordEncoder.encode(password);

        userDTO.setPassword(enPassword);
        Long userId = userService.join(userDTO);
        //user.setRole("USER");
        //repository.save(user);
        return "redirect:/";
    }


    @GetMapping("/profile")
    public void userprofile(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("===프로필보기");
        log.info(userDetails);
        UserResponseDTO userResponseDTO = userService.findUser(userDetails.getUsername());
        model.addAttribute("user", userResponseDTO );
        log.info("==사진위치확인");
        log.info(userResponseDTO.getUrl());
           }
}