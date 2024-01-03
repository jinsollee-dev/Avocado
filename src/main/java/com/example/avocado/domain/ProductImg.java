package com.example.avocado.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "product")
@Table(name = "product_image")

public class ProductImg extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fno;
    private String filename;
    private String uuid;
    private String imgUrl;
    private String repimgYn; //대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "pno")
    private Product product;  //product_pno


    public void updateImg(String filename, String uuid, String imgUrl) {
        this.filename = filename;
        this.uuid = uuid;
        this.imgUrl = imgUrl;
    }
    public String getLink(){
            return "s_"+uuid+"_"+filename;

    }
}
