package com.example.avocado.dto.product;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.avocado.dto.product.QMainProductDto is a Querydsl Projection type for MainProductDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMainProductDto extends ConstructorExpression<MainProductDto> {

    private static final long serialVersionUID = 748248083L;

    public QMainProductDto(com.querydsl.core.types.Expression<Long> pno, com.querydsl.core.types.Expression<String> pname, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<Long> price, com.querydsl.core.types.Expression<String> writer, com.querydsl.core.types.Expression<String> imgUrl) {
        super(MainProductDto.class, new Class<?>[]{long.class, String.class, String.class, long.class, String.class, String.class}, pno, pname, content, price, writer, imgUrl);
    }

}

