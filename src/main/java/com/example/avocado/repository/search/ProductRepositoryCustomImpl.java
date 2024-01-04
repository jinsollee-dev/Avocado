package com.example.avocado.repository.search;

import com.example.avocado.domain.QProduct;
import com.example.avocado.domain.QProductImg;
import com.example.avocado.dto.product.MainProductDto;
import com.example.avocado.dto.product.ProductSearchDTO;
import com.example.avocado.dto.product.QMainProductDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public ProductRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }


    private BooleanExpression ProductNameLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null : QProduct.product.pname.like("%" + searchQuery + "%");
    }
    @Override
    public Page<MainProductDto> getMainProductPage(ProductSearchDTO productSearchDTO, Pageable pageable) {
        QProduct product = QProduct.product;
        QProductImg productImg = QProductImg.productImg;


        QueryResults<MainProductDto> result = queryFactory
                .select(
                        new QMainProductDto(
                                product.pno,
                                product.pname,
                                product.content,
                                product.price,
                                product.writer,
                                productImg.imgUrl
                                )
                )
                .from(productImg)
                .join(productImg.product, product)
                .where(productImg.repimgYn.eq("Y"))
                .where(ProductNameLike(productSearchDTO.getSearchQuery()))
                .orderBy(product.pno.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainProductDto> content = result.getResults();
        long total = result.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression WriterNameLike(String searchQuery) {

        return StringUtils.isEmpty(searchQuery) ? null : QProduct.product.writer.like("%" + searchQuery + "%");
    }
    public Page<MainProductDto> getSellerProductPage(ProductSearchDTO productSearchDTO, Pageable pageable) {
        QProduct product = QProduct.product;
        QProductImg productImg = QProductImg.productImg;

        QueryResults<MainProductDto> result = queryFactory
                .select(
                        new QMainProductDto(
                                product.pno,
                                product.pname,
                                product.content,
                                product.price,
                                product.writer,
                                productImg.imgUrl
                        )
                )
                .from(productImg)
                .join(productImg.product, product)
                .where(productImg.repimgYn.eq("Y"))
                .where(WriterNameLike(productSearchDTO.getSearchQuery()))
                .orderBy(product.pno.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainProductDto> content = result.getResults();
        long total = result.getTotal();
        return new PageImpl<>(content, pageable, total);
    }


}
