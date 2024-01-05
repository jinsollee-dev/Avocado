package com.example.avocado.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartListDto {

    private Long cartItemId;
    private String itemName;
    private Long price;
    private String imgUrl;
    private Long pno;

    public CartListDto(Long cartItemId, String itemName, Long price, String imgUrl, Long pno){
        this.cartItemId = cartItemId;
        this.itemName = itemName;
        this.price = price;
        this.imgUrl = imgUrl;
        this.pno = pno;
    }
}
