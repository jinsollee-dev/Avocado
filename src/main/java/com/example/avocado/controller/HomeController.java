package com.example.avocado.controller;


import com.example.avocado.dto.product.MainProductDto;
import com.example.avocado.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Optional;
import com.example.avocado.dto.product.ProductSearchDTO;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;

    @GetMapping("/")
    public String home(ProductSearchDTO productSearchDTO,
                       Optional<Integer> page, Model model){
           Pageable pageable = PageRequest.of
                   (page.isPresent() ? page.get() : 0, 6);
            Page<MainProductDto> products = productService.getMainProductPage(productSearchDTO, pageable);

            model.addAttribute("products", products);
            model.addAttribute("ProductSearchDto", productSearchDTO);
            model.addAttribute("maxPage", 5);

        return "home";

    }


}