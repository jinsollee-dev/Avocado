package com.example.avocado.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> {
    private int page;  //현재 페이지
    private int size; //한 페이지당 글 수
    private int total; //전체 글 수

    private int start; //한 블럭의 시작 페이지
    private int end; //한 블럭의 끝 페이지

    private boolean prev; //이전 페이지가 나오는지 여부

    private boolean next; //다음 페이지가 나오는지 여부

    private List<E> dtoList;  //제네릭으로 boardList 넣거나 replyList를 넣을 수 있도록 함



    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total){

        if(total <= 0){
            return;
        }

        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.total = total;
        this.dtoList = dtoList;

        this.end = (int)(Math.ceil(this.page / 10.0 )) *  10;
        this.start = this.end - 9;

        int last =  (int)(Math.ceil((total/(double)size)));
        this.end =  end > last ? last: end;

        this.prev = this.start > 1;
        this.next =  total > this.end * this.size;
    }
}
