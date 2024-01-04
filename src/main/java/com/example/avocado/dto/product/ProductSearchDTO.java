package com.example.avocado.dto.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductSearchDTO {
    private String searchDateType;
    private String searchBy;
    private String searchQuery = "";

}
