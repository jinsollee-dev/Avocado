package com.example.avocado.controller;

import com.example.avocado.config.auth.PrincipalDetails;
import com.example.avocado.domain.User;

import com.example.avocado.dto.CartItemDto;
import com.example.avocado.dto.CartListDto;
import com.example.avocado.dto.product.ProductDTO;
import com.example.avocado.service.CartService;
import com.example.avocado.service.ProductService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
  @GetMapping(value = "/{pno}")
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
}

//
//  // 상품 상세 페이지
//  @GetMapping(value = "/{pno}")
//  @ResponseBody
//  public ResponseEntity itemDetail(
//           @Valid CartItemDto cartItemDto,
//          BindingResult bindingResult,
//          Model model, @PathVariable("pno") Long pno,
//          Principal principal) {
//
//
//    ProductDTO productDTO = productService.getProductDetail(pno);
//
//    if (bindingResult.hasErrors()) {
//      StringBuilder sb = new StringBuilder();
//      List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//      for (FieldError fieldError : fieldErrors) {
//        sb.append(fieldError.getDefaultMessage());
//      }
//      return  new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
//    }
//
//    Long cartItemId;
//
//    try {
//      cartItemId = cartService.addCart(cartItemDto, principal.getName());
//
//    } catch (Exception e) {
//      e.printStackTrace();
//      return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//
//    List<CartListDto> cartListDtos = cartService.getCartList(principal.getName());
//    log.info(cartListDtos);
//
//    for (CartListDto cartitem : cartListDtos) {
//      if (cartitem.getPno() == pno) {
//        model.addAttribute("mypick", pno);
//        model.addAttribute("cartitemId", cartitem.getCartItemId());
//      }
//    }
//    model.addAttribute("cartList", cartListDtos);
//    model.addAttribute("product", productDTO);
//    return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);


//  }
//}
