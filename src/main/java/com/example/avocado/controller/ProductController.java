package com.example.avocado.controller;

import com.example.avocado.config.auth.PrincipalDetails;
import com.example.avocado.domain.ReplyRoom;
import com.example.avocado.domain.User;

import com.example.avocado.dto.CartListDto;
import com.example.avocado.dto.product.ProductDTO;
import com.example.avocado.dto.product.ProductImgDTO;
import com.example.avocado.dto.user.UserResponseDTO;
import com.example.avocado.repository.ReplyRoomRepository;
import com.example.avocado.service.CartService;
import com.example.avocado.service.ProductService;
import com.example.avocado.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;
    private final ReplyRoomRepository replyRoomRepository;
    private final ModelMapper modelMapper;

    @GetMapping("/register")
    public void register(User user, Model model,
                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
        UserResponseDTO userResponseDTO = userService.findUser(principalDetails.getUsername());
        String writer = userResponseDTO.getNickname();
        model.addAttribute("writer", writer);
        return;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("productDTO") @Valid ProductDTO productDTO,
                           BindingResult bindingResult, Model model,
                           @AuthenticationPrincipal PrincipalDetails principalDetails) {
        // PrincipalDetails에서 사용자의 username을 가져와서 ProductDTO에 설정
        productDTO.setUsername(principalDetails.getUsername());

        // 유효성검사
        if (bindingResult.hasErrors()) {
            model.addAttribute("productDTO", productDTO);
            return "/product/register";
        }

        productService.insert(productDTO);
        return "redirect:/";
    }


    // 상품 상세 페이지
    @GetMapping("/{pno}")
    public String itemDetail(Model model, @PathVariable("pno") Long pno,
                             Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ProductDTO productDTO = productService.getProductDetail(pno);
        UserResponseDTO userResponseDTO  = userService.findbyWriter(productDTO.getWriter());


        List<CartListDto> cartListDtos = cartService.getCartList(userDetails.getUsername());
        log.info(cartListDtos);

        for (CartListDto cartitem : cartListDtos) {
            if (cartitem.getPno() == pno) {
                model.addAttribute("mypick", pno);
                model.addAttribute("cartitemId", cartitem.getCartItemId());
            }
        }
        model.addAttribute("seller", userResponseDTO);
        model.addAttribute("cartList", cartListDtos);
        model.addAttribute("product", productDTO);
        return "product/view";
    }


    // 수정 화면
    @GetMapping("/modify")
    public String itemDetailmodify(Model model, @RequestParam("pno") Long pno,
                                   Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ProductDTO productDTO = productService.getProductDetail(pno);


        List<CartListDto> cartListDtos = cartService.getCartList(userDetails.getUsername());
        log.info(cartListDtos);

        for (CartListDto cartitem : cartListDtos) {
            if (cartitem.getPno() == pno) {
                model.addAttribute("mypick", pno);
                model.addAttribute("cartitemId", cartitem.getCartItemId());
            }
        }

        model.addAttribute("cartList", cartListDtos);
        model.addAttribute("product", productDTO);
        return "product/modify";
    }

    @PostMapping("/update")
    public String modify(@ModelAttribute("productDTO") ProductDTO productDTO, Model model) {

        log.info("================확인");
        log.info(productDTO.getPno());

        try {
            productService.updateProduct(productDTO);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "redirect:/product/modify?pno=" + productDTO.getPno();
        }
        return "redirect:/product/" + productDTO.getPno();
    }


//    @GetMapping("/done/{pno}")
//    public String done(@PathVariable("pno") Long pno, Model model) {
//        productService.updatedealstatus(pno);
//        ProductDTO productDTO = productService.getProductDetail(pno);
//
//
//        model.addAttribute("product", productDTO);
//        return "redirect:/user/deal";
//    }


    // 상품 문의 하기
    @GetMapping("/reply/{pno}")
    public String productreply(Model model, @PathVariable("pno") Long pno,
                               Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserResponseDTO userResponseDTO = userService.findUser(userDetails.getUsername());
        ProductDTO productDTO = productService.getProductDetail(pno);
        List<ProductImgDTO> imageList = productDTO.getProductImgDtoList();
        ProductImgDTO img1 = imageList.get(0);
        String writer = productDTO.getWriter();
        UserResponseDTO writerResponseDTO = userService.findbyWriter(writer);
        model.addAttribute("seller", writerResponseDTO);
        model.addAttribute("buyer", userResponseDTO);
        model.addAttribute("product", productDTO);
        model.addAttribute("viewimg", img1);

        return "product/reply";
    }


    @GetMapping("/remove")
    public String remove(Long pno) {
        productService.remove(pno);
        return "redirect:/";
    }


    @GetMapping("/admin") //product controller
    public String adminPage(Model model, Authentication authentication, Pageable pageable) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserResponseDTO userResponseDTO = productService.findUser(userDetails.getUsername());
        // User == Admin.Role 일 경우
        if (userResponseDTO.getRole().equals("ADMIN")) {
            List<ProductDTO> productList = productService.getList(pageable);
            model.addAttribute("user", userResponseDTO);
            model.addAttribute("productList", productList);
            return "product/adminPage";
        } else {
            return "redirect:/";
        }
    }


    @PostMapping("/change/{id}") //usercontroller
    public String userChange(@PathVariable Long id, User user) {
        userService.userUpdate(id, user);

        return "/";
    }


    @GetMapping("/dealdone/{id}/{rid}")
    public String dealstatuschange(@PathVariable("id") Long id,
                                   @PathVariable("rid") Long rid,
                                   Authentication authentication) {

        log.info("판매완료처리확인");
        log.info(id);  //구매자 id값
        log.info(rid);     //댓글방 rid

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();  //판매자정보
        UserResponseDTO userResponseDTO = productService.findUser(userDetails.getUsername());

        ReplyRoom replyRoom = replyRoomRepository.findById(rid).get();
        Long pno = replyRoom.getProduct().getPno();
        log.info(pno);

        User user = userService.finduid(id);
        String buyer = user.getNickname(); //구매자 nickname값으로 buyer에 넣어주기
        String seller = userResponseDTO.getNickname(); //판매자 nickname값으로 seller에 넣어주기

        Long dno = productService.updatedealstatus(pno, buyer, seller);
        return "redirect:/user/replylist";

    }


    @GetMapping("/answer/{id}/{rid}")
    public String registeranswer(@PathVariable("id") Long id,
                                 @PathVariable("rid") Long rid,
                                 Authentication authentication,
                                Model model) {

        log.info("답글달기페이지가기");
        log.info(id);  //구매자 id값
        log.info(rid);     //댓글방 rid
        ReplyRoom replyRoom = replyRoomRepository.findById(rid).get();
        Long pno = replyRoom.getProduct().getPno();
        log.info(pno);


        UserDetails userDetails = (UserDetails) authentication.getPrincipal();  //판매자정보
        UserResponseDTO writerResponseDTO = userService.findUser(userDetails.getUsername());
        ProductDTO productDTO = productService.getProductDetail(pno);
        List<ProductImgDTO> imageList = productDTO.getProductImgDtoList();
        ProductImgDTO img1 = imageList.get(0);
        User user = userService.finduid(id);
        UserResponseDTO sellerUserResponseDTO = modelMapper.map(user, UserResponseDTO.class);

        model.addAttribute("rid", rid);
        model.addAttribute("seller", writerResponseDTO);
        model.addAttribute("buyer", sellerUserResponseDTO);
        model.addAttribute("product", productDTO);
        model.addAttribute("viewimg", img1);
        return "product/answer";

    }
}


