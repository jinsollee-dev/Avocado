package com.example.avocado.controller;

import com.example.avocado.config.auth.PrincipalDetails;
import com.example.avocado.domain.User;

import com.example.avocado.dto.CartListDto;
import com.example.avocado.dto.product.ProductDTO;
import com.example.avocado.service.CartService;
import com.example.avocado.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

  private final CartService cartService;
  private final ProductService productService;

  @GetMapping("/register")
  public void register(User user, Model model,
                       @AuthenticationPrincipal PrincipalDetails principalDetails) {
    model.addAttribute("username", principalDetails.getUsername());
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
    return "redirect:/product/"+ productDTO.getPno();
  }


  @GetMapping("/done/{pno}")
  public String done(@PathVariable("pno") Long pno, Model model){
    productService.updatedealstatus(pno);
    ProductDTO productDTO = productService.getProductDetail(pno);


    model.addAttribute("product", productDTO);
    return "redirect:/user/deal";
  }

  @GetMapping("/remove")
  public String remove(Long pno) {
    productService.remove(pno);
    return "redirect:/";
  }
}


