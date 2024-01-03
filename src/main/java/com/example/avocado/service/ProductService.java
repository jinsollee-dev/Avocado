package com.example.avocado.service;


import com.example.avocado.dto.product.MainProductDto;
import com.example.avocado.dto.product.ProductDTO;
import com.example.avocado.dto.product.ProductSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

  void insert(ProductDTO productDTO);

  Long register(ProductDTO productDTO);

  //검색, 페이징 처리한 후 목록 보기
  Page<MainProductDto> getMainProductPage(ProductSearchDTO productSearchDTO, Pageable pageable);

  //목록조회
  List<ProductDTO> getList(Pageable pageable);

  ProductDTO getProduct(Long pno);

  Long modify(ProductDTO productDTO);

  void remove(Long pno);


  //  //dto->entity
//  default Product dtoToEntity(ProductDTO productDTO) {
//    User user = User.builder().id(productDTO.getSellerId()).build();
//    Product product = Product.builder()
//        .pname(productDTO.getPname())
//        .content(productDTO.getContent())
//        .price(productDTO.getPrice())
//        .area(productDTO.getArea())
//        .hope_location(productDTO.getHope_location())
//        .deal_method(productDTO.getDeal_method())
//        .deal_status(productDTO.getDeal_status())
//        .id(user)
//        .build();
//    return product;
//  }
//
//  //entity->dto
//  default ProductDTO entityToDto(Product product, User user) {
//    ProductDTO productDTO = ProductDTO.builder()
//        .pno(product.getPno())
//        .pname(product.getPname())
//        .content(product.getContent())
//        .price(product.getPrice())
//        .area(product.getArea())
//        .hope_location(product.getHope_location())
//        .deal_method(product.getDeal_method())
//        .deal_status(product.getDeal_status())
//        .sellerId(user.getId())
//        .build();
//    return productDTO;
//  }
}