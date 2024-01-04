package com.example.avocado.dto.product;

import com.example.avocado.domain.ProductImg;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@RequiredArgsConstructor
public class ProductImgDTO {
  private Long fno;
  private String filename;
  private String imgUrl;
  private String uuid;


  private static ModelMapper modelMapper = new ModelMapper();

  @Builder
  public ProductImgDTO(String filename, String uuid, String imgUrl) {
    this.filename = filename;
    this.uuid = uuid;
    this.imgUrl = imgUrl;

  }


  // Entity -> DTO
  public static ProductImgDTO of(ProductImg productImg) {
    return modelMapper.map(productImg,ProductImgDTO.class);
  }

  public ProductImgDTO toEntity(ProductImgDTO entity) {
    ProductImgDTO dto = ProductImgDTO.builder()
        .filename(entity.getFilename())
        .imgUrl(entity.getImgUrl())
        .uuid(entity.getUuid())
        .build();
    return dto;
  }

}