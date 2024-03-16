package com.example.avocado.controller;

import com.example.avocado.domain.Dealstatus;
import com.example.avocado.domain.ReplyRoom;
import com.example.avocado.domain.User;
import com.example.avocado.dto.PageRequestDTO;
import com.example.avocado.dto.product.MainProductDto;
import com.example.avocado.dto.product.ProductSearchDTO;
import com.example.avocado.dto.user.UserDTO;
import com.example.avocado.dto.user.UserResponseDTO;
import com.example.avocado.dto.user.UserUpdateDTO;
import com.example.avocado.repository.UserRepository;
import com.example.avocado.service.DealstatusSerivce;
import com.example.avocado.service.ProductService;
import com.example.avocado.service.ReplyService;
import com.example.avocado.service.UserService;
import com.example.avocado.validator.CheckUserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/user")
public class UserController {
    private final UserRepository repository;
    private final UserService userService;
    private final CheckUserValidator checkUserValidator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProductService productService;
    private final ReplyService replyService;
    private final DealstatusSerivce dealstatusSerivce;

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
        return "user/login";
    }

    //프로필 보기
    @GetMapping("/profile")
    public void userprofile(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("===프로필보기");
        log.info(userDetails);
        UserResponseDTO userResponseDTO = userService.findUser(userDetails.getUsername());
        model.addAttribute("user", userResponseDTO);
        log.info("==사진위치확인");
        log.info(userResponseDTO.getUrl());
    }

    //회원 정보 수정
    @PostMapping("/update")
    public String update(UserUpdateDTO userUpdateDTO, Authentication authentication) {

        String password = userUpdateDTO.getPassword();
        String enPassword = bCryptPasswordEncoder.encode(password);
        userUpdateDTO.setPassword(enPassword);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        userService.update(userDetails.getUsername(), userUpdateDTO);
        return "redirect:/user/profile";
    }

    @GetMapping("/withdrawal")
    public void memberWithdrawalForm() {
    }


    //회원 탈퇴
    @PostMapping("/withdrawal")
    public String memberWithdrawal(@RequestParam String password, Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boolean result = userService.withdrawal(userDetails.getUsername(), password);

        if (result) {
            return "redirect:/logout";
        } else {
            model.addAttribute("wrongPassword", "비밀번호가 맞지 않습니다.");
            return "/user/withdrawal";
        }
    }

    //판매자 정보 보기
    @GetMapping("/sellerinfo")
    public String itemDetail(ProductSearchDTO productSearchDTO,
                             Optional<Integer> page,
                             Model model) {
        log.info("=========이름확인");

        productSearchDTO.setSearchDateType("writer");
        log.info(productSearchDTO.getSearchQuery());

        log.info(productSearchDTO);
        String writer = productSearchDTO.getSearchQuery();

        Pageable pageable = PageRequest.of
                (page.isPresent() ? page.get() : 0, 6);
        Page<MainProductDto> products = productService.getSellerProductPage(productSearchDTO, pageable);

        model.addAttribute("products", products);
        model.addAttribute("ProductSearchDto", productSearchDTO);
        model.addAttribute("maxPage", 5);
        model.addAttribute("writer", writer);

        UserResponseDTO userResponseDTO = userService.findbyWriter(writer);
        model.addAttribute("user", userResponseDTO);
        log.info("==사진위치확인");
        log.info(userResponseDTO.getUrl());
        return "/user/sellerinfo";
    }


    @GetMapping("/deal")
    public String itemDetaildeal(ProductSearchDTO productSearchDTO,
                                 Optional<Integer> page,
                                 Model model,
                                 Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserResponseDTO userResponseDTO = userService.findUser(userDetails.getUsername());
        String writer = userResponseDTO.getNickname();
        productSearchDTO.setSearchQuery(writer);
        productSearchDTO.setSearchDateType("writer");
        log.info(productSearchDTO.getSearchQuery());

        log.info(productSearchDTO);


        Pageable pageable = PageRequest.of
                (page.isPresent() ? page.get() : 0, 6);
        Page<MainProductDto> products = productService.getSellerProductPage(productSearchDTO, pageable);

        model.addAttribute("products", products);
        model.addAttribute("ProductSearchDto", productSearchDTO);
        model.addAttribute("maxPage", 5);
        model.addAttribute("writer", writer);


        model.addAttribute("user", userResponseDTO);
        log.info("==사진위치확인");
        log.info(userResponseDTO.getUrl());
        return "user/deal";
    }


    @GetMapping("/admin") //usercontroller
    public String adminPage(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserResponseDTO userResponseDTO = userService.findUser(userDetails.getUsername());
        // User == Admin.Role 일 경우
        if (userResponseDTO.getRole().equals("ADMIN")) {
            List<User> userList = userService.userList();
            model.addAttribute("user", userResponseDTO);
            model.addAttribute("userList", userList);
            return "user/adminPage";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/change/{id}") //usercontroller
    public String userChange(@PathVariable Long id, User user) {
        userService.userUpdate(id, user);

        return "redirect:/";
    }


    @GetMapping("/replylist")
    public String replyelist(ProductSearchDTO productSearchDTO,
                             Optional<Integer> page,
                             Model model,
                             Authentication authentication,
                             PageRequestDTO pageRequestDTO) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserResponseDTO userResponseDTO = userService.findUser(userDetails.getUsername());
        String writer = userResponseDTO.getNickname();
        productSearchDTO.setSearchQuery(writer);
        productSearchDTO.setSearchDateType("writer");
        log.info(productSearchDTO.getSearchQuery());

        log.info(productSearchDTO);


        Pageable pageable = PageRequest.of
                (page.isPresent() ? page.get() : 0, 6);
        Page<MainProductDto> products = productService.getSellerProductPage(productSearchDTO, pageable);

        List<MainProductDto> updatelist = new ArrayList<>();
        for (MainProductDto productDtos : products) {
            List<ReplyRoom> replyRooms = replyService.replyelist(productDtos.getPno());
            productDtos.setReplylist(replyRooms);
            log.info("댓글방확인");
            log.info(replyRooms);
            updatelist.add(productDtos);
            log.info(productDtos);

        }
        log.info(updatelist);
        log.info(products);

        model.addAttribute("updatelist", updatelist);
        model.addAttribute("products", products);
        model.addAttribute("ProductSearchDto", productSearchDTO);
        model.addAttribute("maxPage", 5);
        model.addAttribute("writer", writer);


        model.addAttribute("user", userResponseDTO);
        log.info("==사진위치확인");
        log.info(userResponseDTO.getUrl());
        return "/user/replylist";
    }


    @GetMapping("/dealdonelist")
    public String dealdonelist(ProductSearchDTO productSearchDTO,
                               Optional<Integer> page,
                               Model model,
                               Authentication authentication,
                               PageRequestDTO pageRequestDTO) {


        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("===확인");

        UserResponseDTO userResponseDTO = userService.findUser(userDetails.getUsername());
        String buyer = userResponseDTO.getNickname();
        List<Dealstatus> dealstatusList = dealstatusSerivce.findmylist(buyer);

        model.addAttribute("user", userResponseDTO);
        model.addAttribute("dealdonelist", dealstatusList);

        return "user/dealdonelist";
    }

}