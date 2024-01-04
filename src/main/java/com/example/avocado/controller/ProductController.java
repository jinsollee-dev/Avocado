package com.example.avocado.controller;

import com.example.avocado.config.auth.PrincipalDetails;
import com.example.avocado.domain.User;

import com.example.avocado.dto.product.ProductDTO;
import com.example.avocado.service.ProductService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

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
  @GetMapping(value = "/{pno}")
  public String itemDetail(Model model, @PathVariable("pno") Long pno) {
    ProductDTO productDTO = productService.getProductDetail(pno);
    model.addAttribute("product", productDTO);
    return "product/view";
  }
}


