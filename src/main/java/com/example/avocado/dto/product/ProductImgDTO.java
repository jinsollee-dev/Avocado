package com.example.avocado.dto.product;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProductImgDTO {
  private Long fno;
  private String filename;
  private String imgUrl;
  private String uuid;


  @Builder
  public ProductImgDTO(String filename, String uuid, String imgUrl) {
    this.filename = filename;
    this.uuid = uuid;
    this.imgUrl = imgUrl;

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