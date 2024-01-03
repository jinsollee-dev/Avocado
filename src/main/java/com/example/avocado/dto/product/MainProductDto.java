package com.example.avocado.dto.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class MainProductDto{
    private Long pno;
    private String pname;
    private String content;
    private Long price;
    private String writer;
    private String imgUrl;

    @QueryProjection
    public MainProductDto(Long pno, String pname, String content,
                       Long price, String writer,String imgUrl) {
        this.pno = pno;
        this.pname = pname;
        this.content = content;
        this.price = price;
        this.writer = writer;
        this.imgUrl = imgUrl;
            }
}
