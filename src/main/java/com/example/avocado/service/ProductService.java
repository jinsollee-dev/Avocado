package com.example.avocado.service;


import com.example.avocado.dto.product.MainProductDto;
import com.example.avocado.dto.product.ProductDTO;
import com.example.avocado.dto.product.ProductSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

  void insert(ProductDTO productDTO);

  Long register(ProductDTO productDTO);

  //검색, 페이징 처리한 후 홈화면 목록 보기
  Page<MainProductDto> getMainProductPage(ProductSearchDTO productSearchDTO, Pageable pageable);

  //selle 정보 보기
  Page<MainProductDto> getSellerProductPage(ProductSearchDTO productSearchDTO, Pageable pageable);

  //view 상품 detail
  public ProductDTO getProductDetail(Long pno);

  //목록조회
  List<ProductDTO> getList(Pageable pageable);

  ProductDTO getProduct(Long pno);

   void updateProduct(ProductDTO productDTO);
  void remove(Long pno);

  void updatedealstatus(Long pno);

  //  //dto->entity
//  default Product dtoToEntity(ProductDTO productDTO) {
//    User user = User.builder().id(productDTO.getSellerId()).build();
//    Product product = Product.builder()
//        .pname(productDTO.getPname())
//        .content(productDTO.getContent())
//        .price(productDTO.getPrice())
//        .area(productDTO.getArea())
//        .hopelocation(productDTO.gethopelocation())
//        .dealmethod(productDTO.getdealmethod())
//        .dealstatus(productDTO.getdealstatus())
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
//        .hopelocation(product.gethopelocation())
//        .dealmethod(product.getdealmethod())
//        .dealstatus(product.getdealstatus())
//        .sellerId(user.getId())
//        .build();
//    return productDTO;
//  }
}