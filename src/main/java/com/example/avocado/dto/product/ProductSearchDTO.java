package com.example.avocado.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchDTO {
    private String searchDateType;
    private String searchBy;
    private String searchQuery = "";

}
