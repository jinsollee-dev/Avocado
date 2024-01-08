package com.example.avocado.service;


import com.example.avocado.dto.product.MainProductDto;
import com.example.avocado.dto.product.ProductDTO;
import com.example.avocado.dto.product.ProductSearchDTO;
import com.example.avocado.dto.user.UserResponseDTO;
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

  Long updatedealstatus(Long pno, String buyer, String seller);


  UserResponseDTO findUser(String username); //productService

  List<ProductDTO> getList2 ();
}