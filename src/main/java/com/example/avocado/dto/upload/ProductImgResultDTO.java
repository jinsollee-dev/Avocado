package com.example.avocado.dto.upload;

import com.example.avocado.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImgResultDTO extends BaseEntity {
    private String uuid;
    private String filename;


    public String getLink(){

            return "s_"+uuid+"_"+filename;

    }
}
