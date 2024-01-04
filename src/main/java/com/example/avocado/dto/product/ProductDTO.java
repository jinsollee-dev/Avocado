package com.example.avocado.dto.product;

import com.example.avocado.domain.Product;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
  private Long pno;
  @NotNull(message = "상품명은 필수 입력 값입니다.")
  private String pname;
  @NotEmpty
  @NotBlank
  private String content;
  @NotNull(message = "가격은 필수 입력 값입니다.")
  private Long price;
  private String area;
  private String hope_location;
  private String deal_method;
  private String deal_status;
  private String writer;
  //수정 시 상품 이미지 정보 저장
  private List<ProductImgDTO> ProductImgDtoList = new ArrayList<>();
  //상품 이미지 아이디 저장
  private List<Long> productImgFno = new ArrayList<>();
  @JoinColumn(name = "username")
  private String username;

  private List<MultipartFile> files;




  @Builder
  public ProductDTO(String pname, String content, Long price, String area,
                    String hope_location, String deal_method, String deal_status) {
    this.pname = pname;
    this.content = content;
    this.price = price;
    this.area = area;
    this.hope_location = hope_location;
    this.deal_method = deal_method;
    this.deal_status = deal_status;
  }

  private static ModelMapper modelMapper = new ModelMapper();


  // Entity -> DTO
  public static ProductDTO of(Product prodcut){
    return modelMapper.map(prodcut, ProductDTO.class);
  }


  public Product toEntity(ProductDTO dto) {
    Product entity = Product.builder()
        .pname(dto.pname)
        .content(dto.content)
        .price(dto.price)
        .area(dto.area)
        .hope_location(dto.hope_location)
        .deal_method(dto.deal_method)
        .deal_status(dto.deal_status)
        .build();
    return entity;
  }
}
