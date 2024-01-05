package com.example.avocado.repository;

import com.example.avocado.domain.CartItem;
import com.example.avocado.dto.CartListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndProductPno(Long cartId, Long pno);

    @Query("select new com.example.avocado.dto.CartListDto(ci.id, i.pname, i.price,  im.imgUrl, i.pno) " +
            "from CartItem ci, ProductImg im " +
            "join ci.product i " +
            "where ci.cart.id = :cartId " +
            "and im.product.pno = ci.product.pno " +
            "and im.repimgYn = 'Y' " +
            "order by ci.regDate desc")
    List<CartListDto> findCartListDto(Long cartId);


}

