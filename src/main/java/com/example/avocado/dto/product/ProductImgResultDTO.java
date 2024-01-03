package com.example.avocado.dto.product;

import com.example.avocado.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImgResultDTO {
    private String uuid;
    private String filename;


    public String getLink(){

            return "s_"+uuid+"_"+filename;

    }
}
