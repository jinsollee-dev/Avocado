package com.example.avocado.repository.search;


import com.example.avocado.dto.product.MainProductDto;
import com.example.avocado.dto.product.ProductSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

       // @QueryProjection 을 이용하여 바로 Dto 객체 반환
    Page<MainProductDto> getMainProductPage(ProductSearchDTO productSearchDTO, Pageable pageable);
}
